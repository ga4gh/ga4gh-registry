package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetServiceByIdResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Implementation> {

    public GetServiceByIdResponseEntityCreatorFactory(Class<Implementation> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    public void loadFactory() {
        HashMap<String, Boolean> serializeImplementation = new HashMap<>();
        serializeImplementation.put("organization", true);
        serializeImplementation.put("description", true);
        serializeImplementation.put("contactUrl", true);

        addDataJoin("standardVersion");
        addDataJoin("implementationCategory");
        addDataJoin("organization");
        setReturnSingleResult(true);
        addSerializerModule(new ImplementationSerializerModule(serializeImplementation));
        addSerializerModule(new OrganizationSerializerModule());
    }

    public void prepareResponseEntityCreator(ResponseEntityCreator<Implementation> responseEntityCreator, Map<String, String> pathVariables) {
        responseEntityCreator
            .getHibernateQueryBuilder()
            .filter("id", pathVariables.get("serviceId"));
    }
}
