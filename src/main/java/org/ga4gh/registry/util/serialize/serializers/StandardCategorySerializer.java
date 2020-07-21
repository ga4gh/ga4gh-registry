package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.StandardCategory;

public class StandardCategorySerializer extends VariableDepthSerializer<StandardCategory> {

    public StandardCategorySerializer() {
        super(StandardCategory.class);
    }

    public StandardCategorySerializer(String[] serializedRelationalAttributes) {
        super(StandardCategory.class, serializedRelationalAttributes);
    }

    @Override
    public void serialize(StandardCategory value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeString(value.getCategory());
    }
}
