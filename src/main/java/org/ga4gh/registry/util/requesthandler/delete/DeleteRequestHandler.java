package org.ga4gh.registry.util.requesthandler.delete;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class DeleteRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    public DeleteRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        // hibernate operation, delete the object at the requested id
        T object = getObjectById();
        SessionFactory sessionFactory = getHibernateUtil().getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        return ResponseEntity.ok().body("");
    }
}
