package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.util.serialize.serializers.ReleaseStatusSerializer;

public class ReleaseStatusSerializerModule extends SimpleModule {

    private static final String NAME = "StandardCategorySerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    public ReleaseStatusSerializerModule() {
        super(NAME);
        addSerializer(ReleaseStatus.class, new ReleaseStatusSerializer());
    }
}