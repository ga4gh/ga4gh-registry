package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.serializers.ImplementationSerializer;

public class ImplementationSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;

    public ImplementationSerializerModule() {
        super();
        addSerializer(Implementation.class, new ImplementationSerializer());
    }

    public ImplementationSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(Implementation.class, new ImplementationSerializer(serializeMappedAttrs));
    }
}