package com.oc.ocframework.data.repository;

public interface GenericDao {
    public <T> T findObjectById(Class<T> ObjectClass, Integer id);
}
