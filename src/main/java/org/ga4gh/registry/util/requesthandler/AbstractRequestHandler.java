package org.ga4gh.registry.util.requesthandler;

import java.util.Map;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.HibernateUtil;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public abstract class AbstractRequestHandler<T extends RegistryModel> implements RequestHandler<T> {

    private Class<T> responseClass;
    private SerializerModuleSet serializerModuleSet;
    private Map<String, String> pathVariables;
    private T requestBody;

    @Autowired
    private HibernateUtil hibernateUtil;
    
    /* Constructor */

    public AbstractRequestHandler(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    /* Custom Methods */

    public T preProcessRequestBody(T requestBody) throws ResourceNotFoundException {
        return requestBody;
    }

    public ResponseEntity<String> createResponseEntity() {
        return ResponseEntity.ok()
            .body("");
    }

    /* Setters and Getters */

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public void setSerializerModuleSet(SerializerModuleSet serializerModuleSet) {
        this.serializerModuleSet = serializerModuleSet;
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }

    public void setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public HibernateUtil getHibernateUtil() {
        return hibernateUtil;
    }
}