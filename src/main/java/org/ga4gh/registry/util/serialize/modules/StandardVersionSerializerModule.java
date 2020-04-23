package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;

import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.util.serialize.serializers.StandardVersionSerializer;

public class StandardVersionSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "StandardVersionSerializerModule";

    public StandardVersionSerializerModule() {
        super();
        addSerializer(StandardVersion.class, new StandardVersionSerializer());
    }

    public StandardVersionSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(StandardVersion.class, new StandardVersionSerializer(serializeMappedAttrs));
    }
}