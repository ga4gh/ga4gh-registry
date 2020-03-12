package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.serializers.OrganizationDeepSerializer;

public class OrganizationDeepSerializerModule extends SimpleModule {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String NAME = "OrganizationDeepSerializerModule";

    public OrganizationDeepSerializerModule() {
        super(NAME);
        addSerializer(Organization.class, new OrganizationDeepSerializer());
    }
}