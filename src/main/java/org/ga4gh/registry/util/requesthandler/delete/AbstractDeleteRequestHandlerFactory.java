package org.ga4gh.registry.util.requesthandler.delete;

import java.util.Map;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.AbstractRequestHandlerFactory;
import org.springframework.http.ResponseEntity;

public abstract class AbstractDeleteRequestHandlerFactory<T extends RegistryModel> extends AbstractRequestHandlerFactory<T> {

    private String idPathParameterName;

    /* Constructor */

    public AbstractDeleteRequestHandlerFactory(Class<T> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        super(responseClass, requestHandlerBeanName);
        setIdPathParameterName(idPathParameterName);
    }

    /* Custom Methods */

    public ResponseEntity<String> handleRequest(Map<String, String> pathVariables) {
        DeleteRequestHandler<T> handler = spawnDeleteRequestHandler();
        handler.setIdPathParameterName(getIdPathParameterName());
        handler.setPathVariables(pathVariables);
        return handler.createResponseEntity();
    }

    @SuppressWarnings("unchecked")
    public DeleteRequestHandler<T> spawnDeleteRequestHandler() {
        return getContext().getBean(getRequestHandlerBeanName(), DeleteRequestHandler.class);
    }

    /* Setters and Getters */

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }   
}