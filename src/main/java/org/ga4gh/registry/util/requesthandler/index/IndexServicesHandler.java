package org.ga4gh.registry.util.requesthandler.index;

import java.util.List;
import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;

public class IndexServicesHandler extends IndexRequestHandler<Implementation> {

    public IndexServicesHandler(Class<Implementation> responseClass, SerializerModuleSet serializerModuleSet, HibernateQuerier<Implementation> querier) {
        super(responseClass, serializerModuleSet, querier);
    }

    public List<Implementation> getResultsFromDb() {
        HibernateQuerier<Implementation> q = getQuerier();
        HibernateQueryBuilder qb = getQueryBuilder();
        
        Map<String, String> queryVars = getRequestVariablesA();
        String type = queryVars.get("type");
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            if (!serviceType.getArtifact().equals("*")) {
                qb.filter("standardVersion.standard.artifact", serviceType.getArtifact());  
            }
            if (!serviceType.getVersion().equals("*")) {
                qb.filter("standardVersion.versionNumber", serviceType.getVersion());
            }
        }
        qb.setResponseClass(q.getTypeClass());
        System.out.println(qb.build());
        q.setQueryString(qb.build());
        return q.getResults();
    }
}