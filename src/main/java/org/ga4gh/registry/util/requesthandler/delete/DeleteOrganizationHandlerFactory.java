package org.ga4gh.registry.util.requesthandler.delete;

import org.ga4gh.registry.model.Organization;

public class DeleteOrganizationHandlerFactory extends AbstractDeleteRequestHandlerFactory<Organization> {

    public DeleteOrganizationHandlerFactory(Class<Organization> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        super(responseClass, requestHandlerBeanName, idPathParameterName);
    }
}