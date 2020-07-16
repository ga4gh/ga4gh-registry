package org.ga4gh.registry.util.requesthandler.index;

import java.util.List;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.response.HibernateQuerier;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class IndexRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    @Autowired
    HibernateQuerier<T> querier;

    @Autowired
    HibernateQueryBuilder queryBuilder;

    public IndexRequestHandler(Class<T> responseClass, SerializerModuleSet serializerModuleSet) {
        super(responseClass, serializerModuleSet);
    }

    public ResponseEntity<String> createResponseEntity() {
        System.out.println("Index Request");
        System.out.println("***");
        ResponseEntity<String> responseEntity = null;

        HibernateQuerier<T> q = getQuerier();
        HibernateQueryBuilder qb = getQueryBuilder();
        q.setQueryString(qb.build());
        List<T> object = q.getResults();
        String serialized = getSerializerModuleSet().serializeObject(object);
        responseEntity = ResponseEntity.ok().body(serialized);
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