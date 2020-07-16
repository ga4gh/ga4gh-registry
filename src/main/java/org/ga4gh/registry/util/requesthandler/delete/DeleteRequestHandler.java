package org.ga4gh.registry.util.requesthandler.delete;

import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.AbstractRequestHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class DeleteRequestHandler<T extends RegistryModel> extends AbstractRequestHandler<T> {

    private String idPathParameterName;

    /* Constructor */

    public DeleteRequestHandler(Class<T> responseClass) {
        super(responseClass);
    }

    /* Custom Methods */

    public ResponseEntity<String> createResponseEntity() {
        UUID id = UUID.fromString(getPathVariables().get(getIdPathParameterName()));

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

    /* Setters and Getters */

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }
}