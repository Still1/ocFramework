package com.oc.ocframework.core.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.ocframework.data.repository.GenericDao;

@Service
public class HomeService {
    @Autowired
    private GenericDao genericDao;
    
    public List<Map<String, Object>> getMenus() {
        String sql = "select id, menu_name from t_menu";
        return this.genericDao.findListOfMapBySql(sql);
    }
}
