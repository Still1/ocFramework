package com.oc.ocframework.data.domain.dto;

/**
 * 通用DTO类，用于前后台数据传输
 */
public class GenericDto {
    private String className;
    private String dataJson;
    
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
}
