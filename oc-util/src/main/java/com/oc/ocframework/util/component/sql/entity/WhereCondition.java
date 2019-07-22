package com.oc.ocframework.util.component.sql.entity;

public class WhereCondition {
    private String name;
    private String value;
    private String secondValue;
    private String op;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getSecondValue() {
        return secondValue;
    }
    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }
}
