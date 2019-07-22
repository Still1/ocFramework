package com.oc.ocframework.data.repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import com.oc.ocframework.data.domain.easyui.DataGridResultSet;
import com.oc.ocframework.util.component.json.OcFrameworkJsonUtil;

public abstract class AbstractGenericDao implements GenericDao {

    @Autowired
    private JdbcOperations jdbcOperations;
    
    @Override
    public List<Map<String, Object>> findListOfMapBySql(String sql, Object... args) {
        return jdbcOperations.queryForList(sql, args);
    }

    @Override
    public List<Map<String, Object>> findListOfMapBySql(String sql) {
        return jdbcOperations.queryForList(sql);
    }
    
    @Override
    public <T> List<T> findListOfObjectBySql(String sql, RowMapper<T> rowMapper) {
        return jdbcOperations.query(sql, rowMapper);
    }

    //XXX It may be better.
    //XXX 也许需要考虑异常处理
    @Override
    public Long findLongBySql(String sql) {
        Map<String, Object> resultMap = jdbcOperations.queryForMap(sql);
        Collection<Object> values = resultMap.values();
        Long result = null;
        if(!values.isEmpty()) {
            Iterator<Object> iterator = values.iterator();
            result = (Long)iterator.next();
        }
        return result;
    }

    @Override
    public String findJsonBySql(String sql) {
        List<Map<String, Object>> resultList = this.findListOfMapBySql(sql);
        String json = OcFrameworkJsonUtil.toJson(resultList);
        return json;
    }

    @Override
    public String findJsonBySql(String sql, String countTotalSql) {
        List<Map<String, Object>> resultList = this.findListOfMapBySql(sql);
        Long total = this.findLongBySql(countTotalSql);
        
        DataGridResultSet dataGridResultSet = new DataGridResultSet();
        dataGridResultSet.setRows(resultList);
        dataGridResultSet.setTotal(total);
        
        String json = OcFrameworkJsonUtil.toJson(dataGridResultSet);
        return json;
    }
}
