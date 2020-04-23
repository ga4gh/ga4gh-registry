package org.ga4gh.registry.util.response.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.ga4gh.registry.util.response.ResponseMapper;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.ServiceTypeSerializerModule;

public class GetServiceTypesResponseCreatorFactory extends ResponseCreatorFactory<Implementation> {

    public GetServiceTypesResponseCreatorFactory(Class<Implementation> typeClass) {
        super(typeClass);
    }

    public void load() {
        this.joinData("standardVersion")
            .joinData("implementationCategory")
            .joinData("organization")
            .filterData("implementationCategory.category", "APIService")
            .addModule(new ImplementationSerializerModule())
            .addModule(new ServiceTypeSerializerModule())
            .performResponsePostProcess();
    }

    public void postProcessResponse(ResponseCreator<Implementation> responseCreator) {
        List<Implementation> implementations = responseCreator.getUnserializedListResult();
        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        ResponseMapper mapper = responseCreator.getResponseMapper();
        responseCreator.setSerializedResponse(mapper.serialize(serviceTypes));
    }
}