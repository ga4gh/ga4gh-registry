package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetOrganizationByIdResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Organization> {

    public GetOrganizationByIdResponseEntityCreatorFactory(Class<Organization> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    public void loadFactory() {
        HashMap<String, Boolean> serializeOrganization = new HashMap<>();
        serializeOrganization.put("implementations", true);

        addDataJoin("implementations");
        setReturnSingleResult(true);
        addSerializerModule(new OrganizationSerializerModule(serializeOrganization));
        addSerializerModule(new ImplementationSerializerModule());
    }

    public void prepareResponseEntityCreator(ResponseEntityCreator<Organization> responseEntityCreator, Map<String, String> pathVariables) {
        System.out.println("Preparing getOrganizationById Response");
        System.out.println(pathVariables.get("organizationId"));
        responseEntityCreator
            .getHibernateQueryBuilder()
            .filter("id", pathVariables.get("organizationId"));
    }
}
