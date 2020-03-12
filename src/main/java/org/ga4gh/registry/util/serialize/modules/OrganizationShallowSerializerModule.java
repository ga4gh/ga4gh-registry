package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.serializers.OrganizationShallowSerializer;

public class OrganizationShallowSerializerModule extends SimpleModule {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String NAME = "OrganizationShallowSerializerModule";

    public OrganizationShallowSerializerModule() {
        super(NAME);
        addSerializer(Organization.class, new OrganizationShallowSerializer());
    }
}