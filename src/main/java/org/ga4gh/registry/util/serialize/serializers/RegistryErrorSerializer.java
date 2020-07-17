package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.RegistryError;

public class RegistryErrorSerializer extends VariableDepthSerializer<RegistryError> {

    public RegistryErrorSerializer() {
        super();
    }

    public RegistryErrorSerializer(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
    }

    @Override
    public void serialize(RegistryError value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "timestamp", value.getTimestamp());
        gen.writeNumberField("status", value.getStatus());
        writeStringIfExists(gen, "error", value.getError());
        writeStringIfExists(gen, "message", value.getMessage());
        writeStringIfExists(gen, "path", value.getPath());
        gen.writeEndObject();
    }
}