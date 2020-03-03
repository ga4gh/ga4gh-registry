package org.ga4gh.implementation.registry.util.serializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.implementation.registry.entity.Standard;
import org.ga4gh.implementation.registry.entity.StandardVersion;

public class StandardSerializer extends StdSerializer<Standard> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StandardSerializer() {
        this(null);
    }

    public StandardSerializer(Class<Standard> t) {
        super(t);
    }

    @Override
    public void serialize(
        Standard standard, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("name", standard.getName());
        jgen.writeStringField("short_desc", standard.getShortDescription());
        jgen.writeStringField("long_desc", standard.getLongDescription());
        jgen.writeStringField("documentation_url", standard.getDocumentationUrl());
        
        if (standard.getStandardCategory() != null) {
            jgen.writeStringField("category", standard.getStandardCategory().getCategory());
        }
        if (standard.getStandardStatus() != null) {
            jgen.writeStringField("status", standard.getStandardStatus().getStatus());
        }
        if (standard.getStandardVersions() != null) {
            jgen.writeFieldName("standard_versions");
            jgen.writeStartArray();
            for (StandardVersion sv: standard.getStandardVersions()) {
                jgen.writeObject(sv);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}