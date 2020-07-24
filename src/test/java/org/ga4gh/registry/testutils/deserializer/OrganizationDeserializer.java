package org.ga4gh.registry.testutils.deserializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.ga4gh.registry.model.Organization;

public class OrganizationDeserializer extends JsonDeserializer<Organization> {

    @Override
    public Organization deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {

        Organization org = new Organization();
        JsonNode node = jp.getCodec().readTree(jp);
        org.setId(node.get("id").asText());
        org.setName(node.get("name").asText());
        org.setShortName(node.get("shortName").asText());
        org.setUrl(node.get("url").asText());       
        return org;
    }
    
}