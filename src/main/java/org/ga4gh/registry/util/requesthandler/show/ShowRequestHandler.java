package org.ga4gh.registry.util.requesthandler.show;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class ShowRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    public ShowRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        validateObjectByIdExists(getIdOnPath());
        validateRequest();
        return ResponseEntity.ok().body(serializeObject(getObjectById(getIdOnPath())));
    }
}