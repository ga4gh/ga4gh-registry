package org.ga4gh.registry.util.requesthandler.put;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PutServiceHandlerFactory extends AbstractPutRequestHandlerFactory<Implementation> {

    @Autowired
    @Qualifier("implementationDeepSerializerModuleSet")
    private SerializerModuleSet serializerModuleSet;

    public PutServiceHandlerFactory(Class<Implementation> responseClass, String requestHandlerBeanName, String idPathParameterName) {
        super(responseClass, requestHandlerBeanName, idPathParameterName);
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }
}