package org.ga4gh.registry.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.model.Organization;
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
            System.out.println(ex);
            System.out.println(ex.toString());
            System.out.println(ex.getMessage());
            System.out.println("**********");
            ex.printStackTrace();
            System.out.println("**********");
            System.out.println("***");
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
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
