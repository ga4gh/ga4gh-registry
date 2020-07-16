package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.AbstractRequestHandlerFactory;
import org.springframework.http.ResponseEntity;

public class AbstractPostRequestHandlerFactory<T extends RegistryModel> extends AbstractRequestHandlerFactory<T> {

    /* Constructor */

    public AbstractPostRequestHandlerFactory(Class<T> responseClass, String requestHandlerBeanName) {
        super(responseClass, requestHandlerBeanName);
    }

    /* Custom Methods */

    public ResponseEntity<String> handleRequest(T requestBody) {
        PostRequestHandler<T> handler = spawnPostRequestHandler();
        handler.setSerializerModuleSet(getSerializerModuleSet());
        handler.setRequestBody(requestBody);
        return handler.createResponseEntity();
    }

    @SuppressWarnings("unchecked")
    public PostRequestHandler<T> spawnPostRequestHandler() {
        return getContext().getBean(getRequestHandlerBeanName(), PostRequestHandler.class);
    }
    
}