package org.ga4gh.registry.util.requesthandler.put;

import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.http.ResponseEntity;

public class PutRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    public PutRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<String> createResponseEntity() {
        T newObject = getRequestBody();
        newObject = preProcessRequestBody(newObject);
        String oldId = getId();
        String newId = newObject.getId();
        getObjectById();
        getHibernateUtil().updateEntityObject(getResponseClass(), oldId, newObject);
        T finalObject = (T) getHibernateUtil().readEntityObject(getResponseClass(), newId);
        return ResponseEntity.ok().body(serializeObject(finalObject));
    }
}
