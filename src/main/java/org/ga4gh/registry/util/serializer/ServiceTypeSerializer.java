package org.ga4gh.registry.util.serializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ga4gh.registry.model.ServiceType;

public class ServiceTypeSerializer extends StdSerializer<ServiceType> {

    private static final long serialVersionUID = 1L;

    public ServiceTypeSerializer() {
        this(null);
    }

    public ServiceTypeSerializer(Class<ServiceType> t) {
        super(t);
    }

    @Override
    public void serialize(ServiceType serviceType, JsonGenerator jgen, 
        SerializerProvider provider)
        throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("group", serviceType.getGroup());
        jgen.writeStringField("artifact", serviceType.getArtifact());
        jgen.writeStringField("version", serviceType.getVersion());
        jgen.writeEndObject();
    }
}