package org.ga4gh.registry.util.requesthandler.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Curie;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.ga4gh.registry.util.uriresolver.URIResolver;
import org.springframework.http.ResponseEntity;

public class ResolveURIHandler extends IndexRequestHandler<Implementation> {

    public ResolveURIHandler(Class<Implementation> responseClass, RegistrySerializerModule serializerModule, HibernateQuerier<Implementation> querier) {
        super(responseClass, serializerModule, querier);
    }

    public ResponseEntity<String> createResponseEntity() {

        try {
            String uriString = getRequestVariablesA().get("uri");
            Curie curie = Curie.fromString(uriString);
            Implementation service = getServiceMatchingCuriePrefix(curie.getPrefix());
            if (service == null) {
                throw new ResourceNotFoundException("There is no registered service with the CURIE prefix: '" + curie.getPrefix() + "'");
            }

            String body = resolveURLForService(service, curie.getId());


            return ResponseEntity.ok().body(body);
        } catch (ParseException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    private Implementation getServiceMatchingCuriePrefix(String curiePrefix) {
        HibernateQuerier<Implementation> q = getQuerier();
        HibernateQueryBuilder qb = getQueryBuilder();
        qb.setResponseClass(q.getTypeClass());
        qb.filter("curiePrefix", curiePrefix);
        q.setQueryString(qb.build());
        return q.getSingleResult();
    }

    private String resolveURLForService(Implementation service, String id) {
        String resolvedURL = null;

        Map<String, String> resolutionMethodByServiceType = new HashMap<>();
        resolutionMethodByServiceType.put("org.ga4gh:drs:1.0.0", "drsv1");
        resolutionMethodByServiceType.put("org.ga4gh:drs:1.1.0", "drsv1");

        try {
            String methodName = resolutionMethodByServiceType.get(service.getServiceType().signature());
            URIResolver resolver = new URIResolver();
            Method method = resolver.getClass().getMethod(methodName, Implementation.class, id.getClass());
            resolvedURL = (String) method.invoke(resolver, service, id);
            
        } catch (NoSuchMethodException ex) {
            throw new BadRequestException("There is no URI resolution method for this service / service type");
        } catch (IllegalAccessException ex) {
            throw new BadRequestException("Could not resolve URI");
        } catch (InvocationTargetException ex) {
            throw new BadRequestException("Could not resolve URI");
        }
        
        return resolvedURL;
    }
}