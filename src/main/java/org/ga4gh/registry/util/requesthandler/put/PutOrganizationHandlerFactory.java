package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PutOrganizationHandlerFactory extends AbstractPutRequestHandlerFactory<Organization> {

    @Autowired
    @Qualifier("organizationDeepSerializerModuleSet")
    private SerializerModuleSet serializerModuleSet;

    public PutOrganizationHandlerFactory(Class<Organization> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        super(responseClass, requestHandlerBeanName, idPathParameterName);
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }
}