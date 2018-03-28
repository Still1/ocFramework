package com.oc.ocframework.data.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oc.ocframework.data.repository.AbstractGenericDao;

@Repository
@Transactional
public class HibernateGenericDao extends AbstractGenericDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public <T> T findObjectById(Class<T> objectClass, Integer id) {
        Session session = this.getCurrentSession();
        T object = session.get(objectClass, id);
        return object;
    }

    @Override
    public void saveOrUpdate(Object obj) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(obj);
    }
}
