package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Implementation;

public class ImplementationSerializer extends VariableDepthSerializer<Implementation> {

    public ImplementationSerializer() {
        super();
    }

    public ImplementationSerializer(boolean deep) {
        super(deep);
    }

    @Override
    public void serialize(Implementation value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId().toString());
        writeStringIfExists(gen, "name", value.getName());
        writeObjectIfExists(gen, "type", value.getServiceType());
        writeObjectIfExists(gen, "organization", value.getOrganization());
        writeStringIfExists(gen, "version", value.getVersion());
        writeStringIfExists(gen, "url", value.getUrl());
        writeStringIfDeep(gen, "description", value.getDescription());
        writeStringIfDeep(gen, "contactUrl", value.getContactUrl());
        writeStringIfDeep(gen, "documentationUrl", value.getDocumentationUrl());
        writeStringIfDeep(gen, "createdAt", value.getCreatedAt());
        writeStringIfDeep(gen, "updatedAt", value.getUpdatedAt());
        writeStringIfDeep(gen, "environment", value.getEnvironment());
        gen.writeEndObject();

    }
}