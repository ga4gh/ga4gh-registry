package org.ga4gh.registry.util.requesthandler.show;

import java.util.Map;
import java.util.UUID;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class ShowRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    private String idPathParameterName;

    public ShowRequestHandler(Class<T> responseClass, SerializerModuleSet serializerModuleSet, String idPathParameterName) {
        super(responseClass, serializerModuleSet);
        setIdPathParameterName(idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        // initial setup
        Map<String, String> pathVariables = getRequestVariablesA();
        SerializerModuleSet serializerModuleSet = getSerializerModuleSet();
        UUID id = UUID.fromString(pathVariables.get(getIdPathParameterName()));
        
        // first hibernate operation, update the passed object
        SessionFactory sessionFactory = getHibernateUtil().getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
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