package org.ga4gh.registry.util.requesthandler;

import java.util.Map;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public abstract class AbstractRequestHandlerFactory<T extends RegistryModel> implements RequestHandlerFactory<T> {

    private ApplicationContext context;
    private Class<T> responseClass;
    private SerializerModuleSet serializerModuleSet;
    private String requestHandlerBeanName;
    
    /* Constructor */

    public AbstractRequestHandlerFactory(Class<T> responseClass, String requestHandlerBeanName) {
        setResponseClass(responseClass);
        setRequestHandlerBeanName(requestHandlerBeanName);
    }

    /* Custom Methods */

    public ResponseEntity<String> emptyResponse() {
        return ResponseEntity.ok().body("");
    }

    /* Override handleRequest Methods */

    public ResponseEntity<String> handleRequest() {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariables) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, Map<String, String> requestVariablesC) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(T requestBody) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariables, T requestBody) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, T requestBody) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, Map<String, String> requestVariablesC, T requestBody) {
        return emptyResponse();
    }

    /* Setters and Getters */

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }

    public void setRequestHandlerBeanName(String requestHandlerBeanName) {
        this.requestHandlerBeanName = requestHandlerBeanName;
    }

    public String getRequestHandlerBeanName() {
        return requestHandlerBeanName;
    }
}