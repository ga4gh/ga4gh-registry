package org.ga4gh.registry.util.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.registry.model.Implementation;

public class ImplementationSerializer extends StdSerializer<Implementation> {
    
    private static final long serialVersionUID = 1L;

    public ImplementationSerializer() {
        this(null);
    }

    public ImplementationSerializer(Class<Implementation> t) {
        super(t);
    }

    @Override
    public void serialize(Implementation implementation, JsonGenerator jgen,
        SerializerProvider provider)
        throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("id", implementation.getId().toString());
            jgen.writeStringField("name", implementation.getName());
            jgen.writeObjectField("type", implementation.getServiceType());
            jgen.writeStringField("description", implementation.getDescription());
            jgen.writeObjectField("organization", implementation.getOrganization());
            jgen.writeStringField("contactUrl", implementation.getContactUrl());
            jgen.writeStringField("documentationUrl", implementation.getDocumentationUrl());
            jgen.writeStringField("createdAt", implementation.getCreatedAt());
            jgen.writeStringField("updatedAt", implementation.getUpdatedAt());
            jgen.writeStringField("environment", implementation.getEnvironment());
            jgen.writeStringField("version", implementation.getVersion());
            jgen.writeStringField("url", implementation.getUrl());
            jgen.writeEndObject();
        }
}