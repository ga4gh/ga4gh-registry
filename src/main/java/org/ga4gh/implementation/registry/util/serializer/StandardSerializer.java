package org.ga4gh.implementation.registry.util.serializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.implementation.registry.model.Standard;
import org.ga4gh.implementation.registry.model.StandardVersion;

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
        jgen.writeStringField("id", standard.getId().toString());
        jgen.writeStringField("name", standard.getName());
        jgen.writeStringField("artifact", standard.getArtifact());
        jgen.writeStringField("oneliner", standard.getOneliner());
        jgen.writeStringField("description", standard.getDescription());
        jgen.writeStringField("documentationUrl", standard.getDocumentationUrl());
        
        if (standard.getStandardCategory() != null) {
            jgen.writeStringField("category", standard.getStandardCategory().getCategory());
        }
        if (standard.getReleaseStatus() != null) {
            jgen.writeStringField("status", standard.getReleaseStatus().getStatus());
        }
        if (standard.getStandardVersions() != null) {
            jgen.writeFieldName("versions");
            jgen.writeStartArray();
            for (StandardVersion sv: standard.getStandardVersions()) {
                jgen.writeObject(sv);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}