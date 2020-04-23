package org.ga4gh.registry.util.serialize.serializers;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class VariableDepthSerializer<T> extends JsonSerializer<T> {

    // private boolean deep;

    Map<String, Boolean> serializeMappedAttrs;

    public VariableDepthSerializer() {
        super();
        serializeMappedAttrs = new HashMap<>();
    }

    public VariableDepthSerializer(Map<String, Boolean> serializeMappedAttrs) {
        super();
        this.serializeMappedAttrs = serializeMappedAttrs;
    }

    public void writeStringIfExists(JsonGenerator gen, String property, String value)
        throws IOException {
        if (value != null) {
            gen.writeStringField(property, value);
        }
    }

    public void writeObjectIfExists(JsonGenerator gen, String property, Object value)
        throws IOException {
        if (value != null) {
            gen.writeObjectField(property, value);
        }
    }

    public void writeStringIfSelected(JsonGenerator gen, String property, String value)
        throws IOException {

        if (serializeMappedAttrs.containsKey(property)) {
            if (serializeMappedAttrs.get(property)) {
                writeStringIfExists(gen, property, value);
            }
        }
    }

    public void writeObjectIfSelected(JsonGenerator gen, String property, Object value)
        throws IOException {
        
        if (serializeMappedAttrs.containsKey(property)) {
            if (serializeMappedAttrs.get(property)) {
                writeObjectIfExists(gen, property, value);
            }
        }
    }
}