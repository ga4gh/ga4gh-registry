package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import com.fasterxml.jackson.databind.module.SimpleModule;

public abstract class VariableDepthSerializerModule extends SimpleModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "VariableDepthSerializerModule";
    private Map<String, Boolean> serializeMappedAttrs;

    public VariableDepthSerializerModule() {
        super(NAME);
    }

    public VariableDepthSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(NAME);
        this.serializeMappedAttrs = serializeMappedAttrs;
    }

    public Map<String, Boolean> getSerializeMappedAttrs() {
        return serializeMappedAttrs;
    }
}
