package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.serializers.ImplementationShallowSerializer;

public class ImplementationShallowSerializerModule extends SimpleModule {

    private static final String NAME = "ImplementationShallowSerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    public ImplementationShallowSerializerModule() {
        super(NAME);
        addSerializer(Implementation.class, new ImplementationShallowSerializer());
    }
}