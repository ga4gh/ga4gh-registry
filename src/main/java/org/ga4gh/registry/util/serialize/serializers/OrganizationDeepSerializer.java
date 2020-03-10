package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;

public class OrganizationDeepSerializer extends JsonSerializer<Organization> {

    public OrganizationDeepSerializer() {
        super();
    }

    @Override
    public void serialize(Organization value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId().toString());
        gen.writeStringField("name", value.getName());
        if (value.getShortName() != null) {
            gen.writeStringField("shortName", value.getShortName());
        }
        gen.writeStringField("url", value.getUrl());

        if (value.getImplementations() != null) {
            gen.writeFieldName("implementations");
            gen.writeStartArray();

            for (Implementation implementation : value.getImplementations()) {
                gen.writeObject(implementation);
            }
            gen.writeEndArray();
        }



        gen.writeEndObject();
    }
}