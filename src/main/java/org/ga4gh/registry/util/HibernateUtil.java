package org.ga4gh.registry.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ConfigurableApplicationContext;
import org.ga4gh.registry.App;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardCategory;
import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.model.StandardVersion;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try {
            
            ConfigurableApplicationContext ctx = App.getContext();
            HibernateConfig hConfig = ctx.getBean(HibernateConfig.class);
            SessionFactory sessionFactory = 
                new Configuration()
                .setProperties(hConfig.getAllProperties())
                .addAnnotatedClass(Implementation.class)
                .addAnnotatedClass(ImplementationCategory.class)
                .addAnnotatedClass(Organization.class)
                .addAnnotatedClass(Standard.class)
                .addAnnotatedClass(StandardCategory.class)
                .addAnnotatedClass(ReleaseStatus.class)
                .addAnnotatedClass(StandardVersion.class)
                .buildSessionFactory();
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}