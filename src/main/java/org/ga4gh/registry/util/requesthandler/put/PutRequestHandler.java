package org.ga4gh.registry.util.requesthandler.put;

import java.util.UUID;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
// import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;

public class PutRequestHandler<T extends RegistryModel> extends RequestHandler<T> {

    public PutRequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        super(responseClass, serializerModule, idPathParameterName);
    }

    public ResponseEntity<String> createResponseEntity() {
        System.out.println("***\n***");
        System.out.println("Creating PUT response entity");

        T newObject = getRequestBody();
        newObject = preProcessRequestBody(newObject);
        // get the old object: the object located at the requested id
        UUID oldUUID = getUUID();
        UUID newUUID = newObject.getId();
        System.out.println(oldUUID);
        System.out.println(newUUID);
        T oldObject = getObjectById();
        System.out.println(oldObject);
        System.out.println(newObject);
        // merge the transient passed object with the object in db
        getHibernateUtil().mergeObject(oldUUID.toString(), newObject);

        // second hibernate operation, get updated db object to pass to client
        Session session = getHibernateUtil().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        T finalObject = session.get(getResponseClass(), newUUID);
        finalObject.lazyLoad();
        session.getTransaction().commit();
        String serialized = serializeObject(finalObject);
        return ResponseEntity.ok().body(serialized);
    }
}
