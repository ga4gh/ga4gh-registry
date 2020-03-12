package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.serializers.ImplementationShallowSerializer;

public class ImplementationShallowSerializerModule extends SimpleModule {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String NAME = "ImplementationShallowSerializerModule";

    public ImplementationShallowSerializerModule() {
        super(NAME);
        addSerializer(Implementation.class, new ImplementationShallowSerializer());
    }
}