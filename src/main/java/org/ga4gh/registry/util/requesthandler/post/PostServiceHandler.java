package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.utils.ImplementationRequestUtils;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceHandler extends PostRequestHandler<Implementation> {

    @Autowired
    ImplementationRequestUtils implementationRequestUtils;

    public PostServiceHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule) {
        super(responseClass, serializerModule);
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
