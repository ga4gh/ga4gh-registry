package org.ga4gh.registry.util.response.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.ga4gh.registry.util.response.ResponseMapper;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.ServiceTypeSerializerModule;

public class GetServiceTypesResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Implementation> {

    public GetServiceTypesResponseEntityCreatorFactory(Class<Implementation> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    @Override
    public void loadFactory() {
        addDataJoin("standardVersion");
        addDataJoin("implementationCategory");
        addDataJoin("organization");
        addDataFilter("implementationCategory.category", "APIService");
        addSerializerModule(new ImplementationSerializerModule());
        addSerializerModule(new ServiceTypeSerializerModule());
        setPerformResultPostProcessing(true);
    }

    @Override
    public void postProcessResponse(ResponseEntityCreator<Implementation> responseEntityCreator) {
        List<Implementation> implementations = responseEntityCreator.getUnserializedListResult();
        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        ResponseMapper mapper = responseEntityCreator.getResponseMapper();
        responseEntityCreator.setSerializedResponse(mapper.serialize(serviceTypes));
    }
}
