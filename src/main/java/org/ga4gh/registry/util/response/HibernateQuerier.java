package org.ga4gh.registry.util.response;

import java.util.List;
import javax.persistence.PersistenceException;
import org.hibernate.query.Query;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateQuerier<T> {

    private final Class<T> typeClass;
    private String queryString;

    public HibernateQuerier(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    public List<T> getResults() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
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
            session.close();
        }
        return results;
    }

    public T getSingleResult() throws ResourceNotFoundException {
        List<T> results = getResults();
        if (results.size() < 1) {
            throw new ResourceNotFoundException("No resource by that id");
        }
        return results.get(0);
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}