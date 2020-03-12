package org.ga4gh.registry.util.serialize.modules;

import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.serializers.OrganizationSerializer;

public class OrganizationSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "OrganizationSerializerModule";

    public OrganizationSerializerModule() {
        super();
        addSerializer(Organization.class, new OrganizationSerializer());
    }

    public OrganizationSerializerModule(boolean deep) {
        super(deep);
        addSerializer(Organization.class, new OrganizationSerializer(deep));
    }
}