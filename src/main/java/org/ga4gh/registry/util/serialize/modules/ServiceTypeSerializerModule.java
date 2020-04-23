package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.serialize.serializers.ServiceTypeSerializer;

public class ServiceTypeSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;

    public ServiceTypeSerializerModule() {
        super();
        addSerializer(ServiceType.class, new ServiceTypeSerializer());
    }

    public ServiceTypeSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(ServiceType.class, new ServiceTypeSerializer(serializeMappedAttrs));
    }

}