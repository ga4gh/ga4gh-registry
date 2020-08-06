package org.ga4gh.registry.testutils.deserializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.ga4gh.registry.model.Standard;

public class StandardDeserializer extends JsonDeserializer<Standard> {

    @Override
    public Standard deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {

        Standard std = new Standard();
        JsonNode node = jp.getCodec().readTree(jp);
        std.setId(node.get("id").asText());
        std.setName(node.get("name").asText());
        std.setSummary(node.get("summary").asText());
        std.setDocumentationUrl(node.get("documentationUrl").asText());   
        return std;
    }
}