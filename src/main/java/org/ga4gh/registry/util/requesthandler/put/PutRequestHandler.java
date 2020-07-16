package org.ga4gh.registry.util.requesthandler.put;

import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.AbstractRequestHandler;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class PutRequestHandler<T extends RegistryModel> extends AbstractRequestHandler<T> {

    private String idPathParameterName;

    /* Constructor */

    public PutRequestHandler(Class<T> responseClass) {
        super(responseClass);
    }

    /* Custom Methods */

    public ResponseEntity<String> createResponseEntity() {
        // set id passed on path to the request body object
        T requestBody = getRequestBody();
        requestBody = preProcessRequestBody(requestBody);
        System.out.println(requestBody);
        SerializerModuleSet serializerModuleSet = getSerializerModuleSet();
        System.out.println(getPathVariables());
        System.out.println(getIdPathParameterName());
        System.out.println(getPathVariables().get(getIdPathParameterName()));
        UUID id = UUID.fromString(getPathVariables().get(getIdPathParameterName()));
        requestBody.setId(id);

        // first hibernate operation, update the passed object
        SessionFactory sessionFactory = getHibernateUtil().getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(requestBody);
        session.getTransaction().commit();

        // second hibernate operation, get updated db object to pass to client
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T object = session.get(getResponseClass(), id);
        session.getTransaction().commit();

        String serialized = serializerModuleSet.serializeObject(object);
        return ResponseEntity.ok().body(serialized);
    }

    /* Setters and Getters */

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }
}