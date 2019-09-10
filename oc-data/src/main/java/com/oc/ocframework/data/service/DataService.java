package com.oc.ocframework.data.service;

import net.sf.jsqlparser.JSQLParserException;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.Map;

public interface DataService {
    public String getDataGridResultSetJson(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException;
    public String getDataGridResultSetJson(String fileName, String sqlName) throws IOException, DocumentException, JSQLParserException;
    public void saveOrUpdate(Object obj);
    public <T> void saveOrUpdate(String dataJson, Class<T> dataClass) throws IllegalArgumentException, IllegalAccessException;
    public <T> T getObjectById(Class<T> objectClass, Integer id);
    public <T> T loadObjectById(Class<T> objectClass, Integer id);
    public <T> void delete(String dataJson, Class<T> dataClass);
}
