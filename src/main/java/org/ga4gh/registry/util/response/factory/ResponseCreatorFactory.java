package org.ga4gh.registry.util.response.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.ga4gh.registry.util.response.ResponseMapper;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.springframework.http.ResponseEntity;

public abstract class ResponseCreatorFactory<T> {

    private Class<T> responseClass;
    private List<String> joins;
    private List<String[]> filters;
    private List<SimpleModule> modules;
    private boolean getSingleResult;
    private boolean performResponsePostProcess;

    public ResponseCreatorFactory(Class<T> responseClass) {
        this.responseClass = responseClass;
        joins = new ArrayList<>();
        filters = new ArrayList<>();
        modules = new ArrayList<>();
        getSingleResult = false;
        performResponsePostProcess = false;
        this.load();
    }

    public void load() {}

    public void postProcessResponse(ResponseCreator<T> responseCreator) {}

    public void prepareResponseCreator(
        ResponseCreator<T> responseCreator,
        Map<String, String> requestVariablesA) {
    }

    public void prepareResponseCreator(
        ResponseCreator<T> responseCreator,
        Map<String, String> requestVariablesA,
        Map<String, String> requestVariablesB) {
    }

    public void prepareResponseCreator(
        ResponseCreator<T> responseCreator,
        Map<String, String> requestVariablesA,
        Map<String, String> requestVariablesB,
        Map<String, String> requestVariablesC) {
    }

    public ResponseCreatorFactory<T> joinData(String property) {
        joins.add(property);
        return this;
    }

    public ResponseCreatorFactory<T> filterData(String property, String value) {
        String[] filter = {property, value};
        filters.add(filter);
        return this;
    }

    public ResponseCreatorFactory<T> addModule(SimpleModule module) {
        modules.add(module);
        return this;
    }

    public ResponseCreatorFactory<T> singleResult() {
        getSingleResult = true;
        return this;
    }

    public ResponseCreatorFactory<T> performResponsePostProcess() {
        performResponsePostProcess = true;
        return this;
    }

    private ResponseCreator<T> buildResponseCreator() {
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

    public ResponseEntity<String> buildResponseEntity() {
        ResponseCreator<T> responseCreator = this.buildResponseCreator();
        responseCreator.buildResponse();
        if (performResponsePostProcess) {
            postProcessResponse(responseCreator);
        }
        return responseCreator.getResponseEntity();
    }

    public ResponseEntity<String> buildResponseEntity(Map<String, String> requestVariablesA) {
        ResponseCreator<T> responseCreator = this.buildResponseCreator();
        prepareResponseCreator(responseCreator, requestVariablesA);
        responseCreator.buildResponse();
        if (performResponsePostProcess) {
            postProcessResponse(responseCreator);
        }
        return responseCreator.getResponseEntity();
    }
}