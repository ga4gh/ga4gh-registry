package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.serialize.serializers.StandardSerializer;

public class StandardSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;

    public StandardSerializerModule() {
        super();
        addSerializer(Standard.class, new StandardSerializer());
    }

    public StandardSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(Standard.class, new StandardSerializer(serializeMappedAttrs));
    }

}