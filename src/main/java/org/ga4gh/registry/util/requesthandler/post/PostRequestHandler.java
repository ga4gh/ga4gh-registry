package org.ga4gh.registry.util.requesthandler.post;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.hibernate.HibernateException;
import org.springframework.http.ResponseEntity;

public class PostRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    public PostRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule) {
        super(responseClass, serializerModule);
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<String> createResponseEntity() {
        ResponseEntity<String> responseEntity = null;
        try {
            T newObject = getRequestBody();
            newObject = preProcessRequestBody(newObject);
            validateObjectByIdDoesNotExist(newObject.getId());
            getHibernateUtil().createEntityObject(getResponseClass(), newObject);
            T object = (T) getHibernateUtil().readEntityObject(getResponseClass(), newObject.getId());
            responseEntity = ResponseEntity.ok().body(serializeObject(object));
        } catch (HibernateException e) {
            throw new BadRequestException("Could not create " + getResponseClass().getSimpleName() + ": " + e.getMessage());
        }
        return responseEntity;
    }
}