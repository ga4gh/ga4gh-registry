package org.ga4gh.registry.util.requesthandler.utils;

import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;

public class TypeFilter {

    public static void filter(String type, HibernateQueryBuilder qb) {
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            if (!serviceType.getArtifact().equals("*")) {
                qb.filter("standardVersion.standard.artifact", serviceType.getArtifact());  
            }
            if (!serviceType.getVersion().equals("*")) {
                qb.filter("standardVersion.versionNumber", serviceType.getVersion());
            }
        }
    }
}