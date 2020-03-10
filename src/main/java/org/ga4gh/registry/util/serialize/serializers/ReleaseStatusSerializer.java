package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.ReleaseStatus;

public class ReleaseStatusSerializer extends JsonSerializer<ReleaseStatus> {

    public ReleaseStatusSerializer() {
        super();
    }

    @Override
    public void serialize(ReleaseStatus value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeString(value.getStatus());
    }
}