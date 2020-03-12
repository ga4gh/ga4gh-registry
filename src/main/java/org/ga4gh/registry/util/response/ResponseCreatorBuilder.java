package org.ga4gh.registry.util.response;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ResponseCreatorBuilder<T> {

    private Class<T> responseClass;
    private List<String> joins;
    private List<String[]> filters;
    private List<SimpleModule> modules;
    private boolean getSingleResult;

    public ResponseCreatorBuilder(Class<T> responseClass) {
        this.responseClass = responseClass;
        joins = new ArrayList<>();
        filters = new ArrayList<>();
        modules = new ArrayList<>();
        getSingleResult = false;
    }

    public ResponseCreatorBuilder<T> joinData(String property) {
        joins.add(property);
        return this;
    }

    public ResponseCreatorBuilder<T> filterData(String property, String value) {
        String[] filter = {property, value};
        filters.add(filter);
        return this;
    }

    public ResponseCreatorBuilder<T> addModule(SimpleModule module) {
        modules.add(module);
        return this;
    }

    public ResponseCreatorBuilder<T> singleResult() {
        getSingleResult = true;
        return this;
    }

    public ResponseCreator<T> buildResponseCreator() {
        ResponseCreator<T> responseCreator = new ResponseCreator<>(responseClass);
        responseCreator.setSingleResult(getSingleResult);
        HibernateQueryBuilder<T> queryBuilder = responseCreator.getHibernateQueryBuilder();
        for (String join : joins) {
            queryBuilder.join(join);
        }
        for (String[] filter : filters) {
            queryBuilder.filter(filter[0], filter[1]);
        }
        ResponseMapper mapper = responseCreator.getResponseMapper();
        for (SimpleModule module : modules) {
            mapper.addModule(module);
        }   
        return responseCreator;
    }
}