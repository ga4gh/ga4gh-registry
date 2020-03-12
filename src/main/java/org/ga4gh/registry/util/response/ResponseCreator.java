package org.ga4gh.registry.util.response;

import java.util.List;

public class ResponseCreator<T> {

    private Class<T> responseClass;
    private boolean singleResult;
    private HibernateQueryBuilder<T> hibernateQueryBuilder;
    private HibernateQuerier<T> hibernateQuerier;
    private ResponseMapper responseMapper;
    private String response;

    public ResponseCreator(Class<T> responseClass) {
        this.responseClass = responseClass;
        singleResult = false;
        hibernateQueryBuilder = new HibernateQueryBuilder<>(this.responseClass);
        hibernateQuerier = new HibernateQuerier<>(this.responseClass);
        responseMapper = new ResponseMapper();
    }

    public ResponseCreator<T> buildResponse() {
        String response = null;
        hibernateQuerier.setQueryString(hibernateQueryBuilder.build());
        responseMapper.registerModules();
        if (singleResult) {
            T toSerialize = hibernateQuerier.getSingleResult();
            response = responseMapper.serialize(toSerialize);
        } else {
            List<T> toSerialize = hibernateQuerier.getResults();
            response = responseMapper.serialize(toSerialize);
        }
        this.response = response;
        return this;
    }

    public HibernateQueryBuilder<T> getHibernateQueryBuilder() {
        return hibernateQueryBuilder;
    }

    public HibernateQuerier<T> getHibernateQuerier() {
        return hibernateQuerier;
    }

    public ResponseMapper getResponseMapper() {
        return responseMapper;
    }

    public void setSingleResult(boolean singleResult) {
        this.singleResult = singleResult;
    }

    public String getResponse() {
        return response;
    }

}