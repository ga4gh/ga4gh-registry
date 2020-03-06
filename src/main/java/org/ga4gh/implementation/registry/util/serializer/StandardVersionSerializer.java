package org.ga4gh.implementation.registry.util.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.implementation.registry.model.StandardVersion;

public class StandardVersionSerializer extends StdSerializer<StandardVersion> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StandardVersionSerializer() {
        this(null);
    }

    public StandardVersionSerializer(Class<StandardVersion> t) {
        super(t);
    }

    @Override
    public void serialize(
        StandardVersion standardVersion, JsonGenerator jgen, 
        SerializerProvider provider)
        throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("id", standardVersion.getId().toString());
            jgen.writeStringField("versionNumber", standardVersion.getVersionNumber());
            jgen.writeStringField("documentationUrl", standardVersion.getDocumentationUrl());
            jgen.writeEndObject();
    }
}