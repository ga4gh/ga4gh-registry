package org.ga4gh.registry.util.requesthandler.delete;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class DeleteRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    public DeleteRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        validateObjectByIdExists(getIdOnPath());
        getHibernateUtil().deleteEntityObject(getResponseClass(), getIdOnPath());
        return ResponseEntity.ok().body("");
    }
}
