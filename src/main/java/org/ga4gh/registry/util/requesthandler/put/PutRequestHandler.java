package org.ga4gh.registry.util.requesthandler.put;

import java.util.Map;
import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class PutRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    private String idPathParameterName;

    public PutRequestHandler(Class<T> responseClass, SerializerModuleSet serializerModuleSet, String idPathParameterName) {
        super(responseClass, serializerModuleSet);
        setIdPathParameterName(idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {

        // initial setup
        Map<String, String> pathVariables = getRequestVariablesA();
        T requestBody = getRequestBody();
        SerializerModuleSet serializerModuleSet = getSerializerModuleSet();

        // preprocess request
        requestBody = preProcessRequestBody(requestBody);

        // set id passed on path to the request body object        
        UUID id = UUID.fromString(pathVariables.get(getIdPathParameterName()));
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

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }
}
