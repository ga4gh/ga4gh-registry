package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PostOrganizationHandlerFactory extends AbstractPostRequestHandlerFactory<Organization> {

    @Autowired
    @Qualifier("organizationDeepSerializerModuleSet")
    private SerializerModuleSet serializerModuleSet;

    public PostOrganizationHandlerFactory(Class<Organization> responseClass, String requestHandlerBeanName) {
        super(responseClass, requestHandlerBeanName);
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }
}