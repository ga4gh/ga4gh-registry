package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetServiceInfoResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Implementation> {

    public GetServiceInfoResponseEntityCreatorFactory(Class<Implementation> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    public void loadFactory() {
        HashMap<String, Boolean> serializeImplementation = new HashMap<>();
        serializeImplementation.put("organization", true);
        serializeImplementation.put("description", true);
        serializeImplementation.put("contactUrl", true);

        addDataJoin("standardVersion");
        addDataJoin("organization");
        addDataFilter("id", Ids.SELF_UUID);
        setReturnSingleResult(true);
        addSerializerModule(new ImplementationSerializerModule(serializeImplementation));
        addSerializerModule(new OrganizationSerializerModule());
    }
}
