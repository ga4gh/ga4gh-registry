package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class VariableDepthSerializer<T> extends JsonSerializer<T> {

    private boolean deep;

    public VariableDepthSerializer() {
        super();
        deep = false;
    }

    public VariableDepthSerializer(boolean deep) {
        super();
        this.deep = deep;
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

    public void writeStringIfDeep(JsonGenerator gen, String property, String value)
        throws IOException {
        if (deep) {
            writeStringIfExists(gen, property, value);
        }
    }

    public void writeObjectIfDeep(JsonGenerator gen, String property, Object value)
        throws IOException {
        if (deep) {
            writeObjectIfExists(gen, property, value);
        }
    }
}