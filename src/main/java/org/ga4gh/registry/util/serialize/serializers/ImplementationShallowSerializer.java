package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Implementation;

public class ImplementationShallowSerializer extends JsonSerializer<Implementation> {

    public ImplementationShallowSerializer() {
        super();
    }

    @Override
    public void serialize(Implementation value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId().toString());
        gen.writeStringField("name", value.getName());
        gen.writeObjectField("type", value.getServiceType());
        
        gen.writeFieldName("organization");
        gen.writeStartObject();
        gen.writeObjectField("name", value.getOrganization().getName());
        gen.writeObjectField("url", value.getOrganization().getUrl());
        gen.writeEndObject();

        gen.writeStringField("version", value.getVersion());

        if (value.getDescription() != null) {
            gen.writeStringField("description", value.getDescription());
        }
        
        if (value.getContactUrl() != null) {
            gen.writeStringField("contactUrl", value.getContactUrl());
        }

        if (value.getDocumentationUrl() != null) {
            gen.writeStringField("documentationUrl", value.getDocumentationUrl());
        }

        if (value.getCreatedAt() != null) {
            gen.writeStringField("createdAt", value.getCreatedAt());
        }

        if (value.getUpdatedAt() != null) {
            gen.writeStringField("updatedAt", value.getUpdatedAt());
        }

        if (value.getEnvironment() != null) {
            gen.writeStringField("environment", value.getEnvironment());
        }

        if (value.getUrl() != null) {
            gen.writeStringField("url", value.getUrl());
        }

        gen.writeEndObject();

    }
}