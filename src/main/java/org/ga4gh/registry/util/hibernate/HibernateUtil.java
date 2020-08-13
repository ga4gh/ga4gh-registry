package org.ga4gh.registry.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardCategory;
import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.model.WorkStream;

public class HibernateUtil {

    @Autowired
    private HibernateConfig hibernateConfig;

    private boolean configured;
    private SessionFactory sessionFactory;

    public HibernateUtil() {
        configured = false;
    }

    @PostConstruct
    public void buildSessionFactory() {
        try {
            SessionFactory sessionFactory = 
                new Configuration()
                .setProperties(hibernateConfig.getAllProperties())
                .addAnnotatedClass(Implementation.class)
                .addAnnotatedClass(Organization.class)
                .addAnnotatedClass(Standard.class)
                .addAnnotatedClass(StandardCategory.class)
                .addAnnotatedClass(ReleaseStatus.class)
                .addAnnotatedClass(StandardVersion.class)
                .addAnnotatedClass(WorkStream.class)
                .buildSessionFactory();
            setSessionFactory(sessionFactory);
            setConfigured(true);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* API Methods */

    public void createEntityObject(Class<? extends RegistryModel> entityClass, Object newObject) throws HibernateException {
        Session session = newTransaction();
        try {
            session.saveOrUpdate(newObject);
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    public RegistryModel readEntityObject(Class<? extends RegistryModel> entityClass, String id) throws HibernateException {
        Session session = newTransaction();
        RegistryModel object = null;
        try {
            object = (RegistryModel) session.get(entityClass, id);
            if (object != null) {
                object.lazyLoad();
            }
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
        return object;
    }

    public void updateEntityObject(Class<? extends RegistryModel> entityClass, String oldId, String newId, RegistryModel newObject) throws HibernateException {
        Session session = newTransaction();
        try {
            // update the object at the existing id
            newObject.setId(oldId);
            session.update(newObject);
            endTransaction(session);
            // update the object's id via manual query
            if (!newId.equals(oldId)) {
                session = newTransaction();
                String updateId =
                "UPDATE " + newObject.getClass().getName()
                + " set id='" + newId + "'"
                + " WHERE id='" + oldId + "'";
                session.createQuery(updateId).executeUpdate();
                endTransaction(session);
            }
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    public void deleteEntityObject(Class<? extends RegistryModel> entityClass, String id) throws HibernateException {
        Session session = newTransaction();
        RegistryModel object = null;
        try {
            object = session.get(entityClass, id);
            session.delete(object);
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    /* private convenience methods */

    private Session newTransaction() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return session;
    }

    private void endTransaction(Session session) {
        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
            session.close();
        }
    }

    public void shutdown() {
        getSessionFactory().close();
    }

    /* Setters and Getters */

    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public boolean getConfigured() {
        return configured;
    }
}
