package org.ga4gh.registry.util.response.factory;

import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetOrganizationsResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Organization> {

    public GetOrganizationsResponseEntityCreatorFactory(Class<Organization> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    public void loadFactory() {
        addSerializerModule(new OrganizationSerializerModule());
    }
}
