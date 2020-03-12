package org.ga4gh.registry.util.serialize.modules;

import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.util.serialize.serializers.StandardVersionSerializer;

public class StandardVersionSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "StandardVersionSerializerModule";

    public StandardVersionSerializerModule() {
        super();
        addSerializer(StandardVersion.class, new StandardVersionSerializer());
    }

    public StandardVersionSerializerModule(boolean deep) {
        super(deep);
        addSerializer(StandardVersion.class, new StandardVersionSerializer(deep));
    }
}