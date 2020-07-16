package org.ga4gh.registry.util.serialize.sets;

import com.fasterxml.jackson.databind.module.SimpleModule;

public interface SerializerModuleSetI {

    public void registerModules();
    public String serializeObject(Object object);
    public void addModule(SimpleModule module);

    
}