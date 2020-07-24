package org.ga4gh.registry.testutils.deserializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.ga4gh.registry.model.ServiceType;

public class ServiceTypeDeserializer extends JsonDeserializer<ServiceType> {

    @Override
    public ServiceType deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {

        ServiceType st = new ServiceType();
        JsonNode node = jp.getCodec().readTree(jp);
        st.setGroup(node.get("group").asText());
        st.setArtifact(node.get("artifact").asText());
        st.setVersion(node.get("version").asText());
        
        return st;
    }
    
}