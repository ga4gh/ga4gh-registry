package org.ga4gh.implementation.registry.util.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.implementation.registry.entity.StandardVersion;

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
            jgen.writeStringField("version_number", standardVersion.getVersionNumber());
            jgen.writeStringField("documentation_url", standardVersion.getDocumentationUrl());
            jgen.writeEndObject();
    }
}