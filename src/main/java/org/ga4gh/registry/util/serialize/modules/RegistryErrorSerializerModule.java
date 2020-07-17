package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import org.ga4gh.registry.model.RegistryError;
import org.ga4gh.registry.util.serialize.serializers.RegistryErrorSerializer;

public class RegistryErrorSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;

    public RegistryErrorSerializerModule() {
        super();
        addSerializer(RegistryError.class, new RegistryErrorSerializer());
    }

    public RegistryErrorSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(RegistryError.class, new RegistryErrorSerializer(serializeMappedAttrs));
    }
}