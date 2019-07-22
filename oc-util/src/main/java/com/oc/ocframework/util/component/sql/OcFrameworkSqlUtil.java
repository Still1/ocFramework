package com.oc.ocframework.util.component.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.gson.reflect.TypeToken;
import com.oc.ocframework.util.component.json.OcFrameworkJsonUtil;
import com.oc.ocframework.util.component.sql.entity.WhereCondition;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class OcFrameworkSqlUtil {
    
    private static Map<String, Document> documentCache = new HashMap<>();
    
    public static String getSqlStatement(String fileName, String sqlName) throws IOException, DocumentException {
        Document document = getSqlFileDocument(fileName);
        String statement = document.valueOf("/statements/sql[@name='" + sqlName + "']").trim();
        return statement;
    }
    
    public static String getSqlStatement(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException {
        String statement = getSqlStatement(fileName, sqlName);
        if(parameterMap != null && !parameterMap.isEmpty()) {
            statement = handleSqlCondition(statement, parameterMap);
        }
        return statement;
    }
    
    /**
     * 获取SQL语句对数组，第一条语句为数据查询语句，第二条语句为总数查询语句
     * 
     * @param fileName SQL配置文件名
     * @param sqlName SQL名称
     * @param parameterMap 查询参数
     * 
     * @return SQL语句对数组
     */
    public static String[] getSqlStatementDual(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException {
        String dataStatement = getSqlStatement(fileName, sqlName, parameterMap);
        String totalStatement = convertToCountTotalSql(dataStatement);
        String[] statementDual = new String[2];
        statementDual[0] = dataStatement;
        statementDual[1] = totalStatement;
        return statementDual;
    }
    
    //XXX 有没有更优雅的写法？
    public static String[] getSqlStatementDual(String fileName, String sqlName) throws IOException, DocumentException, JSQLParserException {
        return getSqlStatementDual(fileName, sqlName, null);
    }
    
    //XXX XML配置改为JSON配置
    private static Document getSqlFileDocument(String fileName) throws IOException, DocumentException {
        Document document = null;
        if(documentCache.containsKey(fileName)) {
            document = documentCache.get(fileName);
        } else {
            //XXX 此处应该可以不同步,同步可能影响效率，不同步会出现多个线程同时put同一份的可能
            synchronized(OcFrameworkSqlUtil.class) {
                if(documentCache.containsKey(fileName)) {
                    document = documentCache.get(fileName);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("config/sql/");
                    stringBuilder.append(fileName);
                    stringBuilder.append(".xml");
                    Resource sqlFile = new ClassPathResource(stringBuilder.toString());
                    InputStream inputStream = sqlFile.getInputStream();
                    SAXReader reader = new SAXReader();
                    document =  reader.read(inputStream);
                    documentCache.put(fileName, document);
                }
            }
        }
        return document;
    }
    
    private static String handleSqlCondition(String sqlStatement, Map<String, String[]> parameterMap) throws JSQLParserException {
        Select select = (Select)CCJSqlParserUtil.parse(sqlStatement);
        PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
        
        if(parameterMap.containsKey("cond")) {
            //XXX 需要异常处理，数组内容可能不符合预期
            //XXX 存在安全问题，应该对where条件限制范围，并校验
            String whereConditionJson = parameterMap.get("cond")[0];
            handleSqlWhereCondition(plainSelect, whereConditionJson);
        }
        if(parameterMap.containsKey("page") && parameterMap.containsKey("rows")) {
            //XXX 需要异常处理
            String page = parameterMap.get("page")[0];
            String rows = parameterMap.get("rows")[0];
            handleSqlLimitCondition(plainSelect, page, rows);
        }
        if(parameterMap.containsKey("sort") && parameterMap.containsKey("order")) {
            //XXX 需要异常处理
            String sort = parameterMap.get("sort")[0];
            String order = parameterMap.get("order")[0];
            handleSqlOrderCondition(plainSelect, sort, order);
        }
        return plainSelect.toString();
    }
    
    private static PlainSelect handleSqlWhereCondition(PlainSelect plainSelect, String whereConditionJson) {
        List<WhereCondition> conditionList = OcFrameworkJsonUtil.fromJson(whereConditionJson, new TypeToken<List<WhereCondition>>() {}.getType());
        Iterator<WhereCondition> iterator = conditionList.iterator();
        while(iterator.hasNext()) {
            WhereCondition whereCondition = iterator.next();
            String op = whereCondition.getOp();
            Expression expression = null;
            switch(op) {
                case "eq" :
                case "like" :
                    BinaryExpression binaryExpression = SqlExpressionFactory.getBinaryExpression(op);
                    binaryExpression.setLeftExpression(new Column(whereCondition.getName()));
                    String whereConditionValue = whereCondition.getValue();
                    if(op.contains("like")) {
                        //TODO 支持%like和like%
                        StringBuilder stringBuilder = new StringBuilder(whereConditionValue);
                        stringBuilder.insert(0, "%");
                        stringBuilder.append("%");
                        whereConditionValue = stringBuilder.toString();
                    }
                    binaryExpression.setRightExpression(new StringValue(whereConditionValue));
                    expression = binaryExpression;
                    break;
                default :
                    //TODO 异常处理
                    break;
            }
            Parenthesis parenthesis = new Parenthesis(expression);
            Expression oldWhere = plainSelect.getWhere();
            if(oldWhere == null) {
                plainSelect.setWhere(parenthesis);
            } else {
                plainSelect.setWhere(new AndExpression(oldWhere, parenthesis));
            }
        }
        return plainSelect;
    }
    
    //XXX 只支持了limit子句分页
    private static PlainSelect handleSqlLimitCondition(PlainSelect plainSelect, String page, String rows) {
        Limit limit = new Limit();
        limit.setRowCount(new LongValue(rows));
        Long offset = (Long.parseLong(page) - 1) * Long.parseLong(rows);
        limit.setOffset(new LongValue(offset));
        plainSelect.setLimit(limit);
        return plainSelect;
    }
    
    //XXX 只支持一个排序条件
    private static PlainSelect handleSqlOrderCondition(PlainSelect plainSelect, String sort, String order) {
        OrderByElement orderByElement = new OrderByElement();
        orderByElement.setExpression(new Column(sort));
        if(order.equals("desc")) {
            orderByElement.setAsc(false);
        }
        List<OrderByElement> orderByElements = new ArrayList<OrderByElement>();
        orderByElements.add(orderByElement);
        plainSelect.setOrderByElements(orderByElements);
        return plainSelect;
    }
    
    private static String convertToCountTotalSql(String statement) throws JSQLParserException {
        Select select = (Select)CCJSqlParserUtil.parse(statement);
        PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
        plainSelect.setLimit(null);
        
        Function function = new Function();
        function.setName("count");
        function.setAllColumns(true);
        SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
        selectExpressionItem.setExpression(function);
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(selectExpressionItem);
        plainSelect.setSelectItems(selectItemList);
        return plainSelect.toString();
    }
}
