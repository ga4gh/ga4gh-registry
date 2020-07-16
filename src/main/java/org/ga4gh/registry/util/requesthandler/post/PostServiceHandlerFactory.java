package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PostServiceHandlerFactory extends AbstractPostRequestHandlerFactory<Implementation> {

    @Autowired
    @Qualifier("implementationDeepSerializerModuleSet")
    private SerializerModuleSet serializerModuleSet;

    public PostServiceHandlerFactory(Class<Implementation> responseClass, String requestHandlerBeanName) {
        super(responseClass, requestHandlerBeanName);
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }
}
