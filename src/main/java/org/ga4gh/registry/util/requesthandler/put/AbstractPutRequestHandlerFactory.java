package org.ga4gh.registry.util.requesthandler.put;

import java.util.Map;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;

public abstract class AbstractPutRequestHandlerFactory<T extends RegistryModel> implements ApplicationContextAware {

    private ApplicationContext context;
    private Class<T> responseClass;
    private String requestHandlerBeanName;
    private String idPathParameterName;
    private SerializerModuleSet serializerModuleSet;

    /* Constructor */

    public AbstractPutRequestHandlerFactory(Class<T> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        this.responseClass = responseClass;
        this.requestHandlerBeanName = requestHandlerBeanName;
        this.idPathParameterName = idPathParameterName;
    }

    /* Custom Methods */

    public ResponseEntity<String> handleRequest(Map<String, String> pathVariables, T requestBody) {
        PutRequestHandler<T> handler = spawnPutRequestHandler();
        handler.setIdPathParameterName(idPathParameterName);
        handler.setSerializerModuleSet(getSerializerModuleSet());
        handler.setPathVariables(pathVariables);
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    @SuppressWarnings("unchecked")
    public PutRequestHandler<T> spawnPutRequestHandler() {
        return getContext().getBean(getRequestHandlerBeanName(), PutRequestHandler.class);
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

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }
}