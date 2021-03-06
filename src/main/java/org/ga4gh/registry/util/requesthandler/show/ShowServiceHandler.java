package org.ga4gh.registry.util.requesthandler.show;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;

public class ShowServiceHandler extends ShowRequestHandler<Implementation> {

    public ShowServiceHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public void validateRequest() throws ResourceNotFoundException {
        Implementation service = getObjectById(getIdOnPath());
        if (!service.getCategory().equals(ImplementationCategory.deployment)) {
            throw new ResourceNotFoundException("No service with id: " + getIdOnPath());
        }
    }
}