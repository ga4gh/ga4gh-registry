package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetOrganizationByIdResponseCreatorFactory extends ResponseCreatorFactory<Organization> {

    public GetOrganizationByIdResponseCreatorFactory(Class<Organization> typeClass) {
        super(typeClass);
    }

    public void load() {
        HashMap<String, Boolean> serializeOrganization = new HashMap<>();
        serializeOrganization.put("implementations", true);

        this.joinData("implementations")
            .singleResult()
            .addModule(new OrganizationSerializerModule(serializeOrganization))
            .addModule(new ImplementationSerializerModule());
    }

    public void prepareResponseCreator(
        ResponseCreator<Organization> responseCreator,
        Map<String, String> pathVariables) {
        responseCreator
            .getHibernateQueryBuilder()
            .filter("id", pathVariables.get("organizationId"));
    }
}