package org.ga4gh.registry.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.response.HibernateQuerier;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.ga4gh.registry.util.response.ResponseCreatorBuilder;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;
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
        ResponseCreatorBuilder<Implementation> builder =
            new ResponseCreatorBuilder<>(Implementation.class)
                .joinData("standardVersion")
                .joinData("implementationCategory")
                .joinData("organization")
                .filterData("implementationCategory.category", "APIService")
                .addModule(new ImplementationSerializerModule())
                .addModule(new OrganizationSerializerModule());
        if (type != null) {
            ServiceType serviceType = new ServiceType(type);
            builder
                .filterData("standardVersion.standard.artifact", serviceType.getArtifact())
                .filterData("standardVersion.versionNumber", serviceType.getVersion());
        }
        return builder
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }

    @GetMapping(path = "/{serviceId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceById(
        @PathVariable("serviceId") String serviceId) {
        
        return new ResponseCreatorBuilder<>(Implementation.class)
            .joinData("standardVersion")
            .joinData("implementationCategory")
            .joinData("organization")
            .filterData("id", serviceId)
            .singleResult()
            .addModule(new ImplementationSerializerModule(true))
            .addModule(new OrganizationSerializerModule())
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }

    @GetMapping(path = "/types",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<ServiceType> getServiceTypes() {
        
        ResponseCreator<Implementation> creator =
            new ResponseCreatorBuilder<>(Implementation.class)
                .joinData("standardVersion")
                .joinData("implementationCategory")
                .joinData("organization")
                .filterData("implementationCategory.category", "APIService")
                .buildResponseCreator();
        HibernateQuerier<Implementation> querier =
            creator.getHibernateQuerier();
        String query = creator.getHibernateQueryBuilder().build();
        querier.setQueryString(query);
        List<Implementation> implementations = querier.getResults();
        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        return serviceTypes;
    }
}
