package org.ga4gh.registry.util.requesthandler.delete;

import java.util.Map;
import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class DeleteRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    private String idPathParameterName;

    public DeleteRequestHandler(Class<T> responseClass, SerializerModuleSet serializerModuleSet, String idPathParameterName) {
        super(responseClass, serializerModuleSet);
        setIdPathParameterName(idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {

        Map<String, String> pathVariables = getRequestVariablesA();
        UUID id = UUID.fromString(pathVariables.get(getIdPathParameterName()));

        // first hibernate operation, get the object at the requested id
        SessionFactory sessionFactory = getHibernateUtil().getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T object = session.get(getResponseClass(), id);
        session.getTransaction().commit();

        // second hibernate operation, delete the object at the requested id
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();

        return ResponseEntity.ok().body("");
    }

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }
}
