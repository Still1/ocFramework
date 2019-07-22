package com.oc.ocframework.data.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public interface GenericDao {
    public <T> T getObjectById(Class<T> objectClass, Integer id);
    public <T> T loadObjectById(Class<T> objectClass, Integer id);
    public List<Map<String, Object>> findListOfMapBySql(String sql, Object... args);
    public List<Map<String, Object>> findListOfMapBySql(String sql);
    public Long findLongBySql(String sql);
    public String findJsonBySql(String sql);
    public String findJsonBySql(String sql, String countTotalSql);
    public <T> List<T> findListOfObjectBySql(String sql, RowMapper<T> rowMapper);
    public void saveOrUpdate(Object obj);
}
