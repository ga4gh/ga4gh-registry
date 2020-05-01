package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.WorkStream;

public class WorkStreamSerializer extends VariableDepthSerializer<WorkStream> {

    public WorkStreamSerializer() {
        super();
    }

    public WorkStreamSerializer(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
    }

    @Override
    public void serialize(WorkStream value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId().toString());
        writeStringIfExists(gen, "name", value.getName());
        writeStringIfExists(gen, "abbreviation", value.getAbbreviation());
        writeObjectIfSelected(gen, "standards", value.getStandards());
        gen.writeEndObject();
    }
}