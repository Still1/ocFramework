package com.oc.ocframework.data.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oc.ocframework.data.repository.GenericDao;

@Repository
@Transactional
public class HibernateGenericDao implements GenericDao {

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
}
