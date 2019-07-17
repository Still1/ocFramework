package com.oc.ocframework.data.service;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import net.sf.jsqlparser.JSQLParserException;

public interface DataService {
    public String getDataGridResultSetJson(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException;
    public void saveOrUpdate(Object obj);
    public <T> void saveOrUpdate(String dataJson, Class<T> dataClass) throws IllegalArgumentException, IllegalAccessException;
    public <T> T getObjectById(Class<T> objectClass, Integer id);
    public <T> T loadObjectById(Class<T> objectClass, Integer id);
}
