package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;

public abstract class VariableDepthSerializerModule extends SimpleModule {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "VariableDepthSerializerModule";
    private boolean deep;

    public VariableDepthSerializerModule() {
        super(NAME);
        deep = false;     
    }

    public VariableDepthSerializerModule(boolean deep) {
        super(NAME);
        this.deep = deep;
    }

    public boolean getDeep() {
        return deep;
    }
}
