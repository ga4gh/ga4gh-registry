package org.ga4gh.registry.util.requesthandler;

import java.util.List;
import java.util.Map;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.HibernateUtil;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class RequestHandler<T extends RegistryModel> implements RequestHandlerI<T> {

    private Class<T> responseClass;
    private Map<String, String> requestVariablesA; // path, query, or header
    private Map<String, String> requestVariablesB; // path, query, or header
    private Map<String, String> requestVariablesC; // path, query, or header
    private T requestBody;
    private SerializerModuleSet serializerModuleSet;

    @Autowired
    private HibernateUtil hibernateUtil;

    /* Constructor */

    @Autowired
    public RequestHandler(Class<T> responseClass, SerializerModuleSet serializerModuleSet) {
        setResponseClass(responseClass);
        setSerializerModuleSet(serializerModuleSet);
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

    public void setRequestVariablesA(Map<String, String> requestVariablesA) {
        this.requestVariablesA = requestVariablesA;
    }

    public Map<String, String> getRequestVariablesA() {
        return requestVariablesA;
    }

    public void setRequestVariablesB(Map<String, String> requestVariablesB) {
        this.requestVariablesB = requestVariablesB;
    }

    public Map<String, String> getRequestVariablesB() {
        return requestVariablesB;
    }

    public void setRequestVariablesC(Map<String, String> requestVariablesC) {
        this.requestVariablesC = requestVariablesC;
    }

    public Map<String, String> getRequestVariablesC() {
        return requestVariablesC;
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

    public void setSerializerModuleSet(SerializerModuleSet serializerModuleSet) {
        this.serializerModuleSet = serializerModuleSet;
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }
}