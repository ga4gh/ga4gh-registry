package org.ga4gh.registry.util.requesthandler.index;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.response.HibernateQuerier;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.http.ResponseEntity;

public class IndexServiceTypesHandler extends IndexRequestHandler<Implementation> {

    public IndexServiceTypesHandler(Class<Implementation> responseClass, SerializerModuleSet serializerModuleSet, HibernateQuerier<Implementation> querier) {
        super(responseClass, serializerModuleSet, querier);
    }

    public ResponseEntity<String> createResponseEntity() {
        ResponseEntity<String> responseEntity = null;
        List<Implementation> implementations = getResultsFromDb();
        Set<ServiceType> serviceTypes = postProcessResults(implementations);
        String serialized = getSerializerModuleSet().serializeObject(serviceTypes);
        responseEntity = ResponseEntity.ok().body(serialized);
        return responseEntity;
    }

    public Set<ServiceType> postProcessResults(List<Implementation> implementations) {

        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        return serviceTypes;
    }
}