package org.ga4gh.registry.util;

import java.util.List;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateQuerier<T> {

    private final Class<T> typeClass;
    private final String queryString;

    public HibernateQuerier(Class<T> typeClass, String queryString) {
        this.typeClass = typeClass;
        this.queryString = queryString;
    }

    public List<T> query() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<T> results = null;

        try {
            session.beginTransaction();
            Query<T> query = session.createQuery(getQueryString(), getTypeClass());
            results = query.getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return results;
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

    public String getQueryString() {
        return queryString;
    }
}