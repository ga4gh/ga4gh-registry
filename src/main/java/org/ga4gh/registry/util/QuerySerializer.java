package org.ga4gh.registry.util;

import java.util.List;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class QuerySerializer<T> {

    private Class<T> responseClass;
    private boolean singleResult;
    private QueryStringBuilder<T> queryStringBuilder;
    private HibernateQuerier<T> hibernateQuerier;
    private ResponseMapper responseMapper;

    public QuerySerializer(Class<T> responseClass) {
        setResponseClass(responseClass);
        setSingleResult(false);
        buildQueryStringBuilder();
        buildHibernateQuerier();
        buildResponseMapper();
    }

    public QuerySerializer<T> join(String property) {
        this.queryStringBuilder.join(property);
        return this;
    }

    public QuerySerializer<T> filter(String property, String value) {
        this.queryStringBuilder.filter(property, value);
        return this;
    }

    public QuerySerializer<T> singleResult() {
        setSingleResult(true);
        return this;
    }

    public QuerySerializer<T> addModule(SimpleModule module) {
        this.responseMapper.addModule(module);
        return this;
    }

    public String queryAndSerialize() {
        String response = null;

        hibernateQuerier.setQueryString(queryStringBuilder.build());
        responseMapper.registerModules();
        if (singleResult) {
            T toSerialize = hibernateQuerier.getSingleResult();
            response = responseMapper.serialize(toSerialize);
        } else {
            List<T> toSerialize = hibernateQuerier.getResults();
            response = responseMapper.serialize(toSerialize);
        }
        return response;
    }

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public void setSingleResult(boolean singleResult) {
        this.singleResult = singleResult;
    }

    public QueryStringBuilder<T> getQueryStringBuilder() {
        return queryStringBuilder;
    }

    public HibernateQuerier<T> getHibernateQuerier() {
        return hibernateQuerier;
    }

    private void buildQueryStringBuilder() {
        queryStringBuilder = new QueryStringBuilder<>(responseClass);
    }

    private void buildHibernateQuerier() {
        hibernateQuerier = new HibernateQuerier<>(responseClass);
    }

    private void buildResponseMapper() {
        responseMapper = new ResponseMapper();
    }
}