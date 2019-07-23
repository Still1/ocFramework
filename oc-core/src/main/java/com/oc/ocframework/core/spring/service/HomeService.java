package com.oc.ocframework.core.spring.service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.ocframework.data.domain.easyui.TreeNode;
import com.oc.ocframework.data.repository.GenericDao;

@Service
public class HomeService {
    @Autowired
    private GenericDao genericDao;
    
    public List<TreeNode> getMenus() {
        //XXX 语句配置化
    	String sql = "select id, menu_name, page_path from t_menu";
        return this.genericDao.findListOfObjectBySql(sql, (ResultSet rs, int rowNum) -> {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(rs.getInt(1));
            treeNode.setText(rs.getString(2));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("pagePath", rs.getString(3));
            treeNode.setAttributes(attributes);
            return treeNode;
        });
    }
}
