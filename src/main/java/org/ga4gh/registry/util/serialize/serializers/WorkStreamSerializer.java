package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.WorkStream;

public class WorkStreamSerializer extends VariableDepthSerializer<WorkStream> {

    private static final long serialVersionUID = 1L;

    public WorkStreamSerializer() {
        super(WorkStream.class);
    }

    public WorkStreamSerializer(String[] serializedRelationalAttributes) {
        super(WorkStream.class, serializedRelationalAttributes);
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
