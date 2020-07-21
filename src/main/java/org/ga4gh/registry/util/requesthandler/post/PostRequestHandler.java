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

    public ResponseEntity<String> createResponseEntity() {
        ResponseEntity<String> responseEntity = null;
        try {
            T requestBody = getRequestBody();
            requestBody = preProcessRequestBody(requestBody);
            // first hibernate operation, create db record from passed request body
            SessionFactory sessionFactory = getHibernateUtil().getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Serializable id = session.save(requestBody);
            session.getTransaction().commit();
            // second hibernate operation, get saved db object to pass to client
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            T object = session.get(getResponseClass(), id);
            object.lazyLoad();
            session.getTransaction().commit();

            String serialized = serializeObject(object);
            responseEntity = ResponseEntity.ok().body(serialized);
        } catch (HibernateException e) {
            throw new BadRequestException("Request could not be completed: " + e.toString());
        }
        return responseEntity;
    }
}