package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Implementation;

public class ImplementationSerializer extends VariableDepthSerializer<Implementation> {

    public ImplementationSerializer() {
        super();
    }

    public ImplementationSerializer(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
    }

    @Override
    public void serialize(Implementation value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId().toString());
        writeStringIfExists(gen, "name", value.getName());
        writeObjectIfExists(gen, "type", value.getServiceType());
        writeObjectIfSelected(gen, "organization", value.getOrganization());
        writeStringIfExists(gen, "version", value.getVersion());
        writeStringIfExists(gen, "url", value.getUrl());
        writeStringIfSelected(gen, "description", value.getDescription());
        writeStringIfSelected(gen, "contactUrl", value.getContactUrl());
        writeStringIfExists(gen, "documentationUrl", value.getDocumentationUrl());
        writeStringIfSelected(gen, "createdAt", value.getCreatedAt());
        writeStringIfSelected(gen, "updatedAt", value.getUpdatedAt());
        writeStringIfSelected(gen, "environment", value.getEnvironment());
        gen.writeEndObject();

    }
}