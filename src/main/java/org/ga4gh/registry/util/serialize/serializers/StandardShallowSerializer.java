package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;

public class StandardShallowSerializer extends JsonSerializer<Standard> {

    public StandardShallowSerializer() {
        super();
    }

    @Override
    public void serialize(Standard value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId().toString());
        gen.writeStringField("name", value.getName());
        gen.writeObjectField("artifact", value.getArtifact());
        gen.writeStringField("oneliner", value.getOneliner());
        gen.writeStringField("documentationUrl", value.getDocumentationUrl());
        gen.writeObjectField("category", value.getStandardCategory());
        gen.writeObjectField("status", value.getReleaseStatus());
        gen.writeEndObject();
    }
}