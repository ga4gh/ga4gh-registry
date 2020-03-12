package org.ga4gh.registry.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.QuerySerializer;
import org.ga4gh.registry.util.QuerySerializerBuilder;
import org.ga4gh.registry.util.HibernateQuerier;
import org.ga4gh.registry.util.serialize.modules.ImplementationShallowSerializerModule;
import org.ga4gh.registry.util.validate.TypeValidator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class Services {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServices(@RequestParam(required = false) String type) {
        
        TypeValidator.validate(type);

        QuerySerializer<Implementation> querySerializer =
            new QuerySerializerBuilder<>(Implementation.class)
                .build()
                .join("standardVersion")
                .join("implementationCategory")
                .join("organization")
                .filter("implementationCategory.category", "APIService")
                .addModule(new ImplementationShallowSerializerModule());
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            querySerializer
                .filter("standardVersion.standard.artifact", serviceType.getArtifact())
                .filter("standardVersion.versionNumber", serviceType.getVersion());
        }
        return querySerializer.queryAndSerialize();
    }

    @GetMapping(path = "/{serviceId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceById(
        @PathVariable("serviceId") String serviceId) {
        
        return new QuerySerializerBuilder<>(Implementation.class)
            .build()
            .join("standardVersion")
            .join("implementationCategory")
            .join("organization")
            .filter("id", serviceId)
            .singleResult()
            .addModule(new ImplementationShallowSerializerModule())
            .queryAndSerialize();
    }

    @GetMapping(path = "/types",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<ServiceType> getServiceTypes() {

        QuerySerializer<Implementation> querySerializer =
            new QuerySerializerBuilder<>(Implementation.class)
                .build()
                .join("standardVersion")
                .join("implementationCategory")
                .join("organization")
                .filter("implementationCategory.category", "APIService");
        
        HibernateQuerier<Implementation> querier = querySerializer.getHibernateQuerier();
        String queryString = querySerializer.getQueryStringBuilder().build();
        querier.setQueryString(queryString);
        List<Implementation> implementations = querier.getResults();
        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        return serviceTypes;
    }
}
