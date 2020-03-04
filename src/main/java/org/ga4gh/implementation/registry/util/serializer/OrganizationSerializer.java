package org.ga4gh.implementation.registry.util.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.implementation.registry.entity.Organization;

public class OrganizationSerializer extends StdSerializer<Organization> {

    private static final long serialVersionUID = 1L;

    public OrganizationSerializer() {
        this(null);
    }

    public OrganizationSerializer(Class<Organization> t) {
        super(t);
    }

    @Override
    public void serialize(Organization organization, JsonGenerator jgen,
        SerializerProvider provider)
        throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("id", organization.getId().toString());
            jgen.writeStringField("name", organization.getName());
            jgen.writeStringField("shortName", organization.getShortName());
            jgen.writeStringField("url", organization.getUrl());
            jgen.writeEndObject();
        }
}