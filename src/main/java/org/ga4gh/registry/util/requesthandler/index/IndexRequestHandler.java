package org.ga4gh.registry.util.requesthandler.index;

import java.util.List;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class IndexRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    HibernateQuerier<T> querier;

    @Autowired
    HibernateQueryBuilder queryBuilder;

    @Autowired
    public IndexRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, HibernateQuerier<T> querier) {
        super(responseClass, serializerModule);
        setQuerier(querier);
    }

    public List<T> getResultsFromDb() {
        HibernateQuerier<T> q = getQuerier();
        HibernateQueryBuilder qb = getQueryBuilder();
        qb.setResponseClass(q.getTypeClass());
        q.setQueryString(qb.build());
        return q.getResults();
    }

    public ResponseEntity<String> createResponseEntity() {
        ResponseEntity<String> responseEntity = null;
        List<T> object = getResultsFromDb();
        responseEntity = ResponseEntity.ok().body(serializeObject(object));
        return responseEntity;
    }

    public void setQuerier(HibernateQuerier<T> querier) {
        this.querier = querier;
    }

    public HibernateQuerier<T> getQuerier() {
        return querier;
    }

    public void setQueryBuilder(HibernateQueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public HibernateQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
}
