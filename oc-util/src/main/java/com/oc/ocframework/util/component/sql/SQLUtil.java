package com.oc.ocframework.util.component.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class SQLUtil {
    
    private static Map<String, Document> documentCache = new HashMap<>();
    
    public static String getStatement(String fileName, String sqlName) throws IOException, DocumentException {
        Document document = getSqlFileDocument(fileName);
        String statement = document.valueOf("/statements/sql[@name='" + sqlName + "']").trim();
        return statement;
    }
    
    private static Document getSqlFileDocument(String fileName) throws IOException, DocumentException {
        Document document = null;
        if(documentCache.containsKey(fileName)) {
            document = documentCache.get(fileName);
        } else {
            synchronized(SQLUtil.class) {
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
}
