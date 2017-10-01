package com.oc.ocframework.data.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

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
}
