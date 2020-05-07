package common.deserializer;

import java.io.IOException;
import java.util.UUID;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.ga4gh.registry.model.Implementation;

public class ImplementationDeserializer extends JsonDeserializer<Implementation> {

    @Override
    public Implementation deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {

        Implementation implementation = new Implementation();
        JsonNode node = jp.getCodec().readTree(jp);
        implementation.setId(UUID.fromString(node.get("id").asText()));
        implementation.setName(node.get("name").asText());
        implementation.setDescription(node.get("description").asText());
        implementation.setContactUrl(node.get("contactUrl").asText());
        implementation.setDocumentationUrl(node.get("documentationUrl").asText());
        implementation.setVersion(node.get("version").asText());
        implementation.setUrl(node.get("url").asText());
        
        return implementation;
    }
    
}