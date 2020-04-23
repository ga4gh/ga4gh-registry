package org.ga4gh.registry.util.response;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

public class ResponseCreator<T> {

    private Class<T> responseClass;
    private boolean singleResult;
    private HibernateQueryBuilder<T> hibernateQueryBuilder;
    private HibernateQuerier<T> hibernateQuerier;
    private ResponseMapper responseMapper;
    private T unserializedSingleResult;
    private List<T> unserializedListResult;
    private String serializedResponse;
    private HttpHeaders httpHeaders;

    public ResponseCreator(Class<T> responseClass) {
        this.responseClass = responseClass;
        singleResult = false;
        hibernateQueryBuilder = new HibernateQueryBuilder<>(this.responseClass);
        hibernateQuerier = new HibernateQuerier<>(this.responseClass);
        responseMapper = new ResponseMapper();
        unserializedSingleResult = null;
        unserializedListResult = null;
        serializedResponse = null;
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }

    public ResponseCreator<T> buildResponse() {
        hibernateQuerier.setQueryString(hibernateQueryBuilder.build());
        responseMapper.registerModules();
        if (singleResult) {
            setUnserializedSingleResult(hibernateQuerier.getSingleResult());
            this.setSerializedResponse(responseMapper.serialize(getUnserializedSingleResult()));
        } else {
            setUnserializedListResult(hibernateQuerier.getResults());
            this.setSerializedResponse(responseMapper.serialize(getUnserializedListResult()));
        }
        return this;
    }

    public ResponseEntity<String> getResponseEntity() {
        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(this.getSerializedResponse());
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

    public void setUnserializedSingleResult(T unserializedSingleResult) {
        this.unserializedSingleResult = unserializedSingleResult;
    }

    public T getUnserializedSingleResult() {
        return unserializedSingleResult;
    }

    public void setUnserializedListResult(List<T> unserializedListResult) {
        this.unserializedListResult = unserializedListResult;
    }

    public List<T> getUnserializedListResult() {
        return unserializedListResult;
    }

    public void setSerializedResponse(String serializedResponse) {
        this.serializedResponse = serializedResponse;
    }

    public String getSerializedResponse() {
        return serializedResponse;
    }
}