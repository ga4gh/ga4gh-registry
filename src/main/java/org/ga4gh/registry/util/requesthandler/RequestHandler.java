package org.ga4gh.registry.util.requesthandler;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.InternalServerError;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.hibernate.HibernateUtil;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class RequestHandler<T extends RegistryModel> implements RequestHandlerI<T> {

    private Class<T> responseClass;
    private Map<String, String> requestVariablesA; // path, query, or header
    private Map<String, String> requestVariablesB; // path, query, or header
    private Map<String, String> requestVariablesC; // path, query, or header
    private T requestBody;
    private RegistrySerializerModule serializerModule;
    private String idPathParameterName;

    @Autowired
    private HibernateUtil hibernateUtil;

    /* Constructor */

    @Autowired
    public RequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule) {
        setResponseClass(responseClass);
        setSerializerModule(serializerModule);
    }

    @Autowired
    public RequestHandler(Class<T> responseClass, RegistrySerializerModule serializerModule, String idPathParameterName) {
        setResponseClass(responseClass);
        setSerializerModule(serializerModule);
        setIdPathParameterName(idPathParameterName);
    }

    /* Custom Methods */

    public String getId() throws BadRequestException {
        String id = null;
        try {
            Map<String, String> pathVariables = getRequestVariablesA();
            id = pathVariables.get(getIdPathParameterName());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("requested id does not conform to UUID format");
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    public T getObjectById() throws BadRequestException, ResourceNotFoundException {
        T object = null;
        object = (T) getHibernateUtil().readEntityObject(getResponseClass(), getId());
        if (object == null) {
            throw new ResourceNotFoundException("no object by id: " + getId().toString());
        }
        return object;
    }

    public String serializeObject(T object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getSerializerModule());
        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerError("Internal server error: could not serialize object as JSON " + e.getMessage());
        }
        return serialized;
    }

    public String serializeObject(List<T> object) {

        System.out.println("***");
        System.out.println("SERIALIZING LIST");
        System.out.println(getSerializerModule());
        System.out.println(getSerializerModule().toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getSerializerModule());
        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerError("Internal server error: could not serialize object as JSON " + e.getMessage());
        }
        return serialized;
    }

    public T preProcessRequestBody(T requestBody) throws ResourceNotFoundException {
        return requestBody;
    }

    public ResponseEntity<String> createResponseEntity() {
        return ResponseEntity.ok()
            .body("");
    }

    /* Setters and Getters */

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public void setRequestVariablesA(Map<String, String> requestVariablesA) {
        this.requestVariablesA = requestVariablesA;
    }

    public Map<String, String> getRequestVariablesA() {
        return requestVariablesA;
    }

    public void setRequestVariablesB(Map<String, String> requestVariablesB) {
        this.requestVariablesB = requestVariablesB;
    }

    public Map<String, String> getRequestVariablesB() {
        return requestVariablesB;
    }

    public void setRequestVariablesC(Map<String, String> requestVariablesC) {
        this.requestVariablesC = requestVariablesC;
    }

    public Map<String, String> getRequestVariablesC() {
        return requestVariablesC;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public HibernateUtil getHibernateUtil() {
        return hibernateUtil;
    }

    public void setSerializerModule(RegistrySerializerModule serializerModule) {
        this.serializerModule = serializerModule;
    }

    public RegistrySerializerModule getSerializerModule() {
        return serializerModule;
    }

    public void setIdPathParameterName(String idPathParameterName) {
        this.idPathParameterName = idPathParameterName;
    }

    public String getIdPathParameterName() {
        return idPathParameterName;
    }
}