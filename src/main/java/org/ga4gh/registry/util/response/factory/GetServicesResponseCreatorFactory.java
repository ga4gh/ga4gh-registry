package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetServicesResponseCreatorFactory extends ResponseCreatorFactory<Implementation> {

    public GetServicesResponseCreatorFactory(Class<Implementation> typeClass) {
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
            .filterData("implementationCategory.category", "APIService")
            .addModule(new ImplementationSerializerModule(serializeImplementation))
            .addModule(new OrganizationSerializerModule());
    }

    public void prepareResponseCreator(
        ResponseCreator<Implementation> responseCreator,
        Map<String, String> queryVariables) {        
        String type = queryVariables.get("type");
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            HibernateQueryBuilder<Implementation> qb = responseCreator.getHibernateQueryBuilder();
            qb.filter("standardVersion.standard.artifact", serviceType.getArtifact());  
            qb.filter("standardVersion.versionNumber", serviceType.getVersion());
        }

    }
}
