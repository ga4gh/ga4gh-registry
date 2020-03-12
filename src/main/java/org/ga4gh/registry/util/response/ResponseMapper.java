package org.ga4gh.registry.util.response;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.exception.InternalServerError;

public class ResponseMapper {

    private ObjectMapper objectMapper;
    private List<SimpleModule> modules;

    public ResponseMapper() {
        objectMapper = new ObjectMapper();
        modules = new ArrayList<>();
    }

    public void addModule(SimpleModule module) {
        modules.add(module);
    }

    public void registerModules() {
        for (SimpleModule module : modules) {
            objectMapper.registerModule(module);
        }
    }

    public String serialize(Object value) throws InternalServerError {
        
        String response = null;
        try {
            response = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError(
                "Internal server error: could not serialize response as JSON");
        }
        return response;
    }
}