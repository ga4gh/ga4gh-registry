package org.ga4gh.registry.util.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.RegistryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class ResponseEntityCreator<T extends RegistryModel> {

    @Autowired
    private HibernateQuerierFactory hibernateQuerierFactory;

    @Autowired
    private HibernateQueryBuilder hibernateQueryBuilder;

    @Autowired
    private ResponseMapper responseMapper;

    private Map<Class<? extends RegistryModel>, String> beanPrefixMap = new HashMap<>(){{
        put(Standard.class, "standards");
        put(Implementation.class, "implementations");
        put(Organization.class, "organizations");
    }};

    private Class<T> responseClass;
    private HibernateQuerier<T> hibernateQuerier;
    private boolean singleResult;
    private T unserializedSingleResult;
    private List<T> unserializedListResult;
    private String serializedResponse;
    private HttpHeaders httpHeaders;

    public ResponseEntityCreator(Class<T> responseClass) {
        this.responseClass = responseClass;
        singleResult = false;
        unserializedSingleResult = null;
        unserializedListResult = null;
        serializedResponse = null;
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,PUT,POST,DELETE");
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void setup() {
        hibernateQuerier = (HibernateQuerier<T>) hibernateQuerierFactory.createHibernateQuerier(beanPrefixMap.get(responseClass));
        hibernateQueryBuilder.setResponseClass(responseClass);
    }

    public ResponseEntityCreator<T> buildResponseEntity() {
        
        hibernateQuerier.setQueryString(hibernateQueryBuilder.build());
        responseMapper.registerModules();
        if (singleResult) {
            setUnserializedSingleResult(hibernateQuerier.getSingleResult());
            this.setSerializedResponse(responseMapper.serialize(getUnserializedSingleResult()));
        } else {
            setUnserializedListResult(hibernateQuerier.getResults());
            this.setSerializedResponse(responseMapper.serialize(getUnserializedListResult()));
        }
        return this;
    }

    public ResponseEntity<String> getResponseEntity() {
        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(this.getSerializedResponse());
    }

    public HibernateQueryBuilder getHibernateQueryBuilder() {
        return hibernateQueryBuilder;
    }

    public HibernateQuerier<T> getHibernateQuerier() {
        return hibernateQuerier;
    }

    public ResponseMapper getResponseMapper() {
        return responseMapper;
    }

    public void setSingleResult(boolean singleResult) {
        this.singleResult = singleResult;
    }

    public void setUnserializedSingleResult(T unserializedSingleResult) {
        this.unserializedSingleResult = unserializedSingleResult;
    }

    public T getUnserializedSingleResult() {
        return unserializedSingleResult;
    }

    public void setUnserializedListResult(List<T> unserializedListResult) {
        this.unserializedListResult = unserializedListResult;
    }

    public List<T> getUnserializedListResult() {
        return unserializedListResult;
    }

    public void setSerializedResponse(String serializedResponse) {
        this.serializedResponse = serializedResponse;
    }

    public String getSerializedResponse() {
        return serializedResponse;
    }
}