package com.oc.ocframework.util.component.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class SqlUtil {
    
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
    
    private static Document getSqlFileDocument(String fileName) throws IOException, DocumentException {
        Document document = null;
        if(documentCache.containsKey(fileName)) {
            document = documentCache.get(fileName);
        } else {
            //XXX 此处应该可以不同步
            synchronized(SqlUtil.class) {
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
            String whereConditionJSON = parameterMap.get("cond")[0];
            handleSqlWhereCondition(plainSelect, whereConditionJSON);
        }
        System.out.println(plainSelect.toString());
        return plainSelect.toString();
    }
    
    private static PlainSelect handleSqlWhereCondition(PlainSelect plainSelect, String whereConditionJSON) {
        Gson gson = new Gson();
        List<WhereCondition> conditionList = gson.fromJson(whereConditionJSON, new TypeToken<List<WhereCondition>>() {}.getType());
        Iterator<WhereCondition> iterator = conditionList.iterator();
        while(iterator.hasNext()) {
            WhereCondition whereCondition = iterator.next();
            String op = whereCondition.getOp();
            switch(op) {
                case "eq" :
                    EqualsTo whereExpression = new EqualsTo();
                    whereExpression.setLeftExpression(new Column(whereCondition.getName()));
                    whereExpression.setRightExpression(new StringValue(whereCondition.getValue()));
                    Parenthesis parenthesis = new Parenthesis(whereExpression);
                    if(plainSelect.getWhere() == null) {
                        plainSelect.setWhere(parenthesis);
                    } else {
                        plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), parenthesis));
                    }
                    break;
            }
        }
        return plainSelect;
    }
    
}
