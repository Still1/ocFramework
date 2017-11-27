package com.oc.ocframework.data.domain.easyui;

import java.util.List;
import java.util.Map;

public class DataGridResultSet {
    private Long total;
    private List<Map<String, Object>> rows;
    
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List<Map<String, Object>> getRows() {
        return rows;
    }
    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
