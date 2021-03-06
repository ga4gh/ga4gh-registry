package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.utils.ImplementationRequestUtils;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;

public class PutServiceHandler extends PutRequestHandler<Implementation> {

    @Autowired
    ImplementationRequestUtils implementationRequestUtils;

    public PutServiceHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public Implementation preProcessRequestBody(Implementation requestBody) throws ResourceNotFoundException {
        return getImplementationRequestUtils().preProcessDeployment(requestBody);
    }

    public void setImplementationRequestUtils(ImplementationRequestUtils implementationRequestUtils) {
        this.implementationRequestUtils = implementationRequestUtils;
    }

    public ImplementationRequestUtils getImplementationRequestUtils() {
        return implementationRequestUtils;
    }
}
