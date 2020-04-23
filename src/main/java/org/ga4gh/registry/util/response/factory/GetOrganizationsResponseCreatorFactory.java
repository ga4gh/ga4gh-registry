package org.ga4gh.registry.util.response.factory;

import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetOrganizationsResponseCreatorFactory extends ResponseCreatorFactory<Organization> {

    public GetOrganizationsResponseCreatorFactory(Class<Organization> typeClass) {
        super(typeClass);
    }

    public void load() {
        this.addModule(new OrganizationSerializerModule());
    }
}