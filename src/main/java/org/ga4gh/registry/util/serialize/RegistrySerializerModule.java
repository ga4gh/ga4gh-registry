package org.ga4gh.registry.util.serialize;

import java.util.List;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class RegistrySerializerModule extends SimpleModule {

    private static final long serialVersionUID = 1L;
    
    public RegistrySerializerModule(String name, Version version, List<JsonSerializer<?>> serializers) {
        super(name, version, serializers);
    }
}
