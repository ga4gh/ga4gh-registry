package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class GetServicesResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Implementation> {

    public GetServicesResponseEntityCreatorFactory(Class<Implementation> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
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
        addDataFilter("implementationCategory.category", "APIService");
        addSerializerModule(new ImplementationSerializerModule(serializeImplementation));
        addSerializerModule(new OrganizationSerializerModule());
    }

    public void prepareResponseEntityCreator(ResponseEntityCreator<Implementation> responseEntityCreator, Map<String, String> queryVariables) {
        String type = queryVariables.get("type");
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            HibernateQueryBuilder qb = responseEntityCreator.getHibernateQueryBuilder();
            qb.filter("standardVersion.standard.artifact", serviceType.getArtifact());  
            qb.filter("standardVersion.versionNumber", serviceType.getVersion());
        }
    }
}