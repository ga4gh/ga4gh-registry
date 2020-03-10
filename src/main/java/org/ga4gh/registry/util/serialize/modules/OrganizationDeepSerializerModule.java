package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.serializers.OrganizationDeepSerializer;

public class OrganizationDeepSerializerModule extends SimpleModule {

    private static final String NAME = "OrganizationDeepSerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    public OrganizationDeepSerializerModule() {
        super(NAME);
        addSerializer(Organization.class, new OrganizationDeepSerializer());
    }
}