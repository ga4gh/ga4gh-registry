package org.ga4gh.registry.util.requesthandler.post;

import java.io.Serializable;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
            getHibernateUtil().createEntityObject(getResponseClass(), newObject);
            T object = (T) getHibernateUtil().readEntityObject(getResponseClass(), newObject.getId());
            String serialized = serializeObject(object);
            responseEntity = ResponseEntity.ok().body(serialized);
        } catch (HibernateException e) {
            throw new BadRequestException("Request could not be completed: " + e.toString());
        }
        return responseEntity;
    }
}