package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.ga4gh.registry.model.StandardVersion;

public class StandardVersionSerializer extends VariableDepthSerializer<StandardVersion> {
    
    public StandardVersionSerializer() {
        super();
    }

    public StandardVersionSerializer(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
    }

    @Override
    public void serialize(StandardVersion value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId().toString());
        writeObjectIfSelected(gen, "standard", value.getStandard());
        writeStringIfExists(gen, "versionNumber", value.getVersionNumber());
        writeStringIfExists(gen, "documentationUrl", value.getDocumentationUrl());
        writeObjectIfExists(gen, "status", value.getReleaseStatus());
        writeObjectIfSelected(gen, "implementations", value.getImplementations());
        gen.writeEndObject();
    }
}