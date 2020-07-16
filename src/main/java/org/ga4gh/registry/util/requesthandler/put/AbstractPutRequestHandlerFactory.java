package org.ga4gh.registry.util.requesthandler.put;

import java.util.Map;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.AbstractRequestHandlerFactory;
import org.springframework.http.ResponseEntity;

public abstract class AbstractPutRequestHandlerFactory<T extends RegistryModel> extends AbstractRequestHandlerFactory<T> {

    private String idPathParameterName;

    /* Constructor */

    public AbstractPutRequestHandlerFactory(Class<T> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        super(responseClass, requestHandlerBeanName);
        setIdPathParameterName(idPathParameterName);
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

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }
}