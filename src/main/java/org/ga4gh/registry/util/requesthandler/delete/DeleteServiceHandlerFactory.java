package org.ga4gh.registry.util.requesthandler.delete;

import org.ga4gh.registry.model.Implementation;

public class DeleteServiceHandlerFactory extends AbstractDeleteRequestHandlerFactory<Implementation> {

    public DeleteServiceHandlerFactory(Class<Implementation> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        super(responseClass, requestHandlerBeanName, idPathParameterName);
    }
}