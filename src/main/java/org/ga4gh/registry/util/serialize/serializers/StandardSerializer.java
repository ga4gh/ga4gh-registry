package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Standard;

public class StandardSerializer extends VariableDepthSerializer<Standard> {

    public StandardSerializer() {
        super();
    }

    public StandardSerializer(boolean deep) {
        super(deep);
    }

    @Override
    public void serialize(Standard value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId().toString());
        writeStringIfExists(gen, "name", value.getName());
        writeObjectIfExists(gen, "artifact", value.getArtifact());
        writeStringIfExists(gen, "summary", value.getOneliner());
        writeStringIfDeep(gen, "description", value.getDescription());
        writeStringIfExists(gen, "documentationUrl", value.getDocumentationUrl());
        writeObjectIfExists(gen, "category", value.getStandardCategory());
        writeObjectIfExists(gen, "status", value.getReleaseStatus());
        writeObjectIfDeep(gen, "versions", value.getStandardVersions());
        gen.writeEndObject();
    }
}