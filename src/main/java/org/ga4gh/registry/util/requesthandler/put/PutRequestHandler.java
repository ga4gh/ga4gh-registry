package org.ga4gh.registry.util.requesthandler.put;

import java.util.Map;
import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class PutRequestHandler<T extends RegistryModel> implements RequestHandler<T> {

    private Class<T> responseClass;
    private String idPathParameterName;
    private SerializerModuleSet serializerModuleSet;
    private Map<String, String> pathVariables;
    private T requestBody;

    @Autowired
    private HibernateUtil hibernateUtil;

    /* Constructor */

    public PutRequestHandler(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    /* Custom Methods */

    public ResponseEntity<String> createResponseEntity() {
        
        // set id passed on path to the request body object
        UUID id = UUID.fromString(getPathVariables().get(getIdPathParameterName()));
        requestBody.setId(id);

        // first hibernate operation, update the passed object
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
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

        return ResponseEntity.ok()
            .body(serialized);
    }

    /* Setters and Getters */

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }

    public void setSerializerModuleSet(SerializerModuleSet serializerModuleSet) {
        this.serializerModuleSet = serializerModuleSet;
    }

    public SerializerModuleSet getSerializerModuleSet() {
        return serializerModuleSet;
    }

    public void setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public T getRequestBody() {
        return requestBody;
    }
}