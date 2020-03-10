package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Organization;

public class OrganizationShallowSerializer extends JsonSerializer<Organization> {

    public OrganizationShallowSerializer() {
        super();
    }

    @Override
    public void serialize(Organization value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId().toString());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("url", value.getUrl());
        gen.writeEndObject();
    }
}