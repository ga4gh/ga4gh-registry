package org.ga4gh.registry.util.hibernate;

import java.util.List;
import javax.persistence.PersistenceException;
import org.hibernate.query.Query;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateQuerier<T> implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private HibernateUtil hibernateUtil;

    private final Class<T> typeClass;
    private String queryString;

    public HibernateQuerier(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    public List<T> getResults() {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<T> results = null;

        try {
            session.beginTransaction();
            Query<T> query = session.createQuery(getQueryString(), getTypeClass());
            results = query.getResultList();
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw new BadRequestException("Invalid search parameter and / or format");
        } finally {
            // session.close();
        }
        return results;
    }

    public T getSingleResult() throws ResourceNotFoundException {
        List<T> results = getResults();
        T result = null;
        if (results.size() < 1) {
            // throw new ResourceNotFoundException("No resource by that id");
        } else {
            result = results.get(0);
        }
        return result;
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }
}
