package org.ga4gh.registry.util.serialize.serializers;

import java.util.HashSet;
import java.util.Set;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class VariableDepthSerializer<T> extends StdSerializer<T> {

    private Set<String> serializedRelationalAttributes;

    public VariableDepthSerializer(Class<T> t) {
        super(t);
        setSerializedRelationalAttributes(new HashSet<>());
    }

    public VariableDepthSerializer(Class<T> t, String[] serializedRelationalAttributes) {
        super(t);
        setSerializedRelationalAttributes(new HashSet<>());
        for (String s : serializedRelationalAttributes) {
            this.serializedRelationalAttributes.add(s);
        }
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

        if (serializedRelationalAttributes.contains(property)) {
            writeStringIfExists(gen, property, value);
        }
    }

    public void writeObjectIfSelected(JsonGenerator gen, String property, Object value)
        throws IOException {

        if (serializedRelationalAttributes.contains(property)) {
            writeObjectIfExists(gen, property, value);
        }
    }

    /* Setters and Getters */

    public void setSerializedRelationalAttributes(Set<String> serializedRelationalAttributes) {
        this.serializedRelationalAttributes = serializedRelationalAttributes;
    }

    public Set<String> getSerializedRelationalAttributes() {
        return serializedRelationalAttributes;
    }
}