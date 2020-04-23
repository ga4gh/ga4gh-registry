package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.serialize.serializers.OrganizationSerializer;

public class OrganizationSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;

    public OrganizationSerializerModule() {
        super();
        addSerializer(Organization.class, new OrganizationSerializer());
    }

    public OrganizationSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(Organization.class, new OrganizationSerializer(serializeMappedAttrs));
    }
}