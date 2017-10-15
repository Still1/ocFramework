package com.oc.ocframework.data.service.impl;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import com.oc.ocframework.data.service.DataService;
import com.oc.ocframework.util.component.sql.SqlUtil;

import net.sf.jsqlparser.JSQLParserException;

@Service
public class DefaultDataService implements DataService {

    @Override
    public String getDataGridResultSetJSON(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException{
        String statement = null;
        if(parameterMap != null && !parameterMap.isEmpty()) {
            statement = SqlUtil.getSqlStatement(fileName, sqlName, parameterMap);
        } else {
            statement = SqlUtil.getSqlStatement(fileName, sqlName);
        }
        return statement;
    }
}
