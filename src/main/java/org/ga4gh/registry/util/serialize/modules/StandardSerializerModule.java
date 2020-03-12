package org.ga4gh.registry.util.serialize.modules;

import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.serialize.serializers.StandardSerializer;

public class StandardSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "StandardSerializerModule";

    public StandardSerializerModule() {
        super();
        addSerializer(Standard.class, new StandardSerializer());
    }

    public StandardSerializerModule(boolean deep) {
        super(deep);
        addSerializer(Standard.class, new StandardSerializer(deep));
    }

}