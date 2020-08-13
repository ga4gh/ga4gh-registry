package org.ga4gh.registry.util.requesthandler.index;

import java.util.List;
import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.requesthandler.utils.TypeFilter;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class IndexImplementationsHandler extends IndexRequestHandler<Implementation> {

    public IndexImplementationsHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, HibernateQuerier<Implementation> querier) {
        super(responseClass, serializerModule, querier);
    }

    public List<Implementation> getResultsFromDb() {
        HibernateQuerier<Implementation> q = getQuerier();
        HibernateQueryBuilder qb = getQueryBuilder();
        Map<String, String> queryVars = getRequestVariablesA();
        qb.filter("category", ImplementationCategory.implementation.toString());
        TypeFilter.filter(queryVars.get("type"), qb);
        qb.setResponseClass(q.getTypeClass());
        q.setQueryString(qb.build());
        return q.getResults();
    }
}