package com.oc.ocframework.data.service;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import net.sf.jsqlparser.JSQLParserException;

public interface DataService {
    public String getDataGridResultSetJson(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException;
}
