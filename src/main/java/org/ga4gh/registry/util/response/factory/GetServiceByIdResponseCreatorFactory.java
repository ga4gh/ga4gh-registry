package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetServiceByIdResponseCreatorFactory extends ResponseCreatorFactory<Implementation> {

    public GetServiceByIdResponseCreatorFactory(Class<Implementation> typeClass) {
        super(typeClass);
    }

    public void load() {
        HashMap<String, Boolean> serializeImplementation = new HashMap<>();
        serializeImplementation.put("organization", true);
        serializeImplementation.put("description", true);
        serializeImplementation.put("contactUrl", true);

        this.joinData("standardVersion")
            .joinData("implementationCategory")
            .joinData("organization")
            .singleResult()
            .addModule(new ImplementationSerializerModule(serializeImplementation))
            .addModule(new OrganizationSerializerModule());
    }

    public void prepareResponseCreator(
        ResponseCreator<Implementation> responseCreator,
        Map<String, String> pathVariables) {
        responseCreator
            .getHibernateQueryBuilder()
            .filter("id", pathVariables.get("serviceId"));
    }
}