package org.ga4gh.registry.util.requesthandler;

import java.util.Map;
import org.ga4gh.registry.model.RegistryModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public class RequestHandlerFactory<T extends RegistryModel> implements RequestHandlerFactoryI<T> {

    private ApplicationContext context;
    private Class<T> responseClass;
    private String requestHandlerBeanName;
    
    /* Constructor */

    public RequestHandlerFactory(Class<T> responseClass, String requestHandlerBeanName) {
        setResponseClass(responseClass);
        setRequestHandlerBeanName(requestHandlerBeanName);
    }

    /* Custom Methods */

    public ResponseEntity<String> emptyResponse() {
        return ResponseEntity.ok().body("");
    }

    @SuppressWarnings("unchecked")
    public RequestHandler<T> spawnRequestHandler() {
        return getContext().getBean(getRequestHandlerBeanName(), RequestHandler.class);
    }

    /* Override handleRequest Methods */

    public ResponseEntity<String> handleRequest() {
        RequestHandler<T> handler = spawnRequestHandler();
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariables) {
        RequestHandler<T> handler = spawnRequestHandler();
        handler.setRequestVariablesA(requestVariables);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, Map<String, String> requestVariablesC) {
        return emptyResponse();
    }

    public ResponseEntity<String> handleRequest(T requestBody) {
        RequestHandler<T> handler = spawnRequestHandler();
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    public ResponseEntity<String> handleRequest(Map<String, String> requestVariables, T requestBody) {
        RequestHandler<T> handler = spawnRequestHandler();
        handler.setRequestVariablesA(requestVariables);
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
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

    public void setRequestHandlerBeanName(String requestHandlerBeanName) {
        this.requestHandlerBeanName = requestHandlerBeanName;
    }

    public String getRequestHandlerBeanName() {
        return requestHandlerBeanName;
    }
}