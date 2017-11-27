package com.oc.ocframework.data.service.impl;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.ocframework.data.repository.GenericDao;
import com.oc.ocframework.data.service.DataService;
import com.oc.ocframework.util.component.sql.SqlUtil;

import net.sf.jsqlparser.JSQLParserException;

@Service
public class DefaultDataService implements DataService {

    @Autowired
    private GenericDao genericDao;
    
    @Override
    public String getDataGridResultSetJson(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException{
        String[] statementDual = SqlUtil.getSqlStatementDual(fileName, sqlName, parameterMap);
        String json = this.genericDao.findJsonBySql(statementDual[0], statementDual[1]);
        return json;
    }
}