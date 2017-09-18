package com.oc.ocframework.data.repository;

import java.util.List;
import java.util.Map;

public interface GenericDao {
    public <T> T findObjectById(Class<T> ObjectClass, Integer id);
    
    public List<Map<String, Object>> findListOfMapBySql(String sql, Object... args);
}
