package com.oc.ocframework.data.domain.dto;

import java.util.List;

/**
 * 通用DTO类，用于前后台数据传输
 */
public class GenericDto {
    private String className;
    private String dataJson;
    private List<String> foreignKey;
    
    
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getDataJson() {
        return dataJson;
    }
    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
    public List<String> getForeignKey() {
        return foreignKey;
    }
    public void setForeignKey(List<String> foreignKey) {
        this.foreignKey = foreignKey;
    }
}
