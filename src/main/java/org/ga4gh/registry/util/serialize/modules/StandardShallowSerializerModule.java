package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.serialize.serializers.StandardShallowSerializer;

public class StandardShallowSerializerModule extends SimpleModule {

    private static final String NAME = "StandardShallowSerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    public StandardShallowSerializerModule() {
        super(NAME);
        addSerializer(Standard.class, new StandardShallowSerializer());
    }
}