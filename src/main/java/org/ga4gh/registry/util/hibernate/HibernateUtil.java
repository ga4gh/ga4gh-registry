package org.ga4gh.registry.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
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
                .addAnnotatedClass(ImplementationCategory.class)
                .addAnnotatedClass(Organization.class)
                .addAnnotatedClass(Standard.class)
                .addAnnotatedClass(StandardCategory.class)
                .addAnnotatedClass(ReleaseStatus.class)
                .addAnnotatedClass(StandardVersion.class)
                .addAnnotatedClass(WorkStream.class)
                .buildSessionFactory();
            System.out.println("Done building, now going to set");
            setSessionFactory(sessionFactory);
            System.out.println("Done setting");
            setConfigured(true);
            System.out.println("Session Factory Build Success");
        } catch (Throwable ex) {
            System.out.println("Session Factory Build Failure");
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* API Methods */

    public void createEntityObject(Class<? extends RegistryModel> entityClass, Object newObject) {
        Session session = newTransaction();
        try {
            session.saveOrUpdate(newObject);
        } catch (Exception e) {
            System.out.println("An exception in 'createEntityObject'" + e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    public RegistryModel readEntityObject(Class<? extends RegistryModel> entityClass, String id) {
        Session session = newTransaction();
        RegistryModel object = null;
        try {
            object = (RegistryModel) session.get(entityClass, id);
            object.lazyLoad();
        } catch (Exception e) {
            System.out.println("An exception in 'readEntityObject'" + e.getMessage());
        } finally {
            endTransaction(session);
        }
        return object;
    }

    public void updateEntityObject(Class<? extends RegistryModel> entityClass, String oldId, Object newObject) {
        try {
            deleteEntityObject(entityClass, oldId);
            createEntityObject(entityClass, newObject);        
        } catch (Exception e) {
            System.out.println("An exception in 'updateEntityObject'" + e.getMessage());
        }
    }

    public void deleteEntityObject(Class<? extends RegistryModel> entityClass, String id) {
        Session session = newTransaction();
        RegistryModel object = null;
        try {
            object = session.get(entityClass, id);
            session.delete(object);
        } catch (Exception e) {
            System.out.println("An exception in 'deleteObject'" + e.getMessage());
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
        session.getTransaction().commit();
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
