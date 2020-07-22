package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.ga4gh.registry.model.ServiceType;

public class ServiceTypeSerializer extends VariableDepthSerializer<ServiceType> {

    private static final long serialVersionUID = 1L;

    public ServiceTypeSerializer() {
        super(ServiceType.class);
    }

    public ServiceTypeSerializer(String[] serializedRelationalAttributes) {
        super(ServiceType.class, serializedRelationalAttributes);
    }

    @Override
    public void serialize(ServiceType value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "group", value.getGroup());
        writeStringIfExists(gen, "artifact", value.getArtifact());
        writeStringIfExists(gen, "version", value.getVersion());
        gen.writeEndObject();
    }

}
