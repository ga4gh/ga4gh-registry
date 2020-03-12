package org.ga4gh.registry.util.serialize.modules;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.serializers.ImplementationSerializer;

public class ImplementationSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "ImplementationSerializerModule";

    public ImplementationSerializerModule() {
        super();
        addSerializer(Implementation.class, new ImplementationSerializer());
    }

    public ImplementationSerializerModule(boolean deep) {
        super(deep);
        addSerializer(Implementation.class, new ImplementationSerializer(deep));
    }
}