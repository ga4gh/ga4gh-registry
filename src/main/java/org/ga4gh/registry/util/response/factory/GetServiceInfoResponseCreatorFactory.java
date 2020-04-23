package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetServiceInfoResponseCreatorFactory extends ResponseCreatorFactory<Implementation> {

    public GetServiceInfoResponseCreatorFactory(Class<Implementation> typeClass) {
        super(typeClass);
    }

    public void load() {
        HashMap<String, Boolean> serializeImplementation = new HashMap<>();
        serializeImplementation.put("organization", true);
        serializeImplementation.put("description", true);
        serializeImplementation.put("contactUrl", true);

        this.joinData("standardVersion")
            .joinData("organization")
            .filterData("id", Ids.SELF_UUID)
            .singleResult()
            .addModule(new ImplementationSerializerModule(serializeImplementation))
            .addModule(new OrganizationSerializerModule());
    }
}