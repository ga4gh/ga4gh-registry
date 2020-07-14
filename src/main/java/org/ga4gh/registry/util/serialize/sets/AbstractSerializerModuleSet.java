package org.ga4gh.registry.util.serialize.sets;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.ga4gh.registry.exception.InternalServerError;

public abstract class AbstractSerializerModuleSet implements SerializerModuleSet {

    private List<SimpleModule> serializerModules;
    private ObjectMapper objectMapper;

    /* Constructor */

    public AbstractSerializerModuleSet() {
        serializerModules = new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    /* Custom Methods */

    public void registerModules() {
        for (SimpleModule module: serializerModules) {
            objectMapper.registerModule(module);
        }
    }

    public String serializeObject(Object object) throws InternalServerError {
        String response = null;
        try {
            response = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError(
                "Internal server error: could not serialize object as JSON");
        }
        return response;
    }

    /* Setters and Getters */

    public void addModule(SimpleModule module) {
        serializerModules.add(module);
    }
    
}