package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Organization;

public class OrganizationSerializer extends VariableDepthSerializer<Organization> {

    public OrganizationSerializer() {
        super();
    }

    public OrganizationSerializer(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
    }

    @Override
    public void serialize(Organization value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId().toString());
        writeStringIfExists(gen, "name", value.getName());
        writeStringIfExists(gen, "shortName", value.getShortName());
        writeStringIfExists(gen, "url", value.getUrl());
        writeObjectIfSelected(gen, "implementations", value.getImplementations());
        gen.writeEndObject();
    }
}