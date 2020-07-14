package org.ga4gh.registry.util.response;

import javax.persistence.PersistenceException;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HibernateWriter<T> implements ApplicationContextAware{

    private ApplicationContext context;

    @Autowired
    private HibernateUtil hibernateUtil;

    private final Class<T> typeClass;

    public HibernateWriter(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    public void write(T object) {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw new BadRequestException("Invalid request body");
        } finally {
            session.close();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }
}