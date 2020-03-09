package org.ga4gh.registry.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ga4gh.registry.annotation.openapi.ApiResponseBadRequest;
import org.ga4gh.registry.annotation.openapi.ApiResponseNotFound;
import org.ga4gh.registry.annotation.openapi.ApiResponseServerError;
import org.ga4gh.registry.annotation.openapi.ParameterType;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.ga4gh.registry.example.Example;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class Services {

    @Operation(summary = "List services",
               description = "Get list of web service implementations of GA4GH standards")
    @ParameterType
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = Implementation.class),
                examples = {
                    @ExampleObject(
                        name = "Services list",
                        summary = "Services list",
                        value = Example.SERVICE_LIST
                    )
                }
            )
        }
    )
    @ApiResponseBadRequest
    @ApiResponseServerError
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Implementation> getServices(
        @RequestParam(required = false) String type
    ) {

        ServiceType serviceType = null;
        if (type != null) {
            try {
                serviceType = new ServiceType(type);
            } catch (InstantiationError e) {
                throw new BadRequestException(e);
            }
        }

        String q = 
            "select i from Implementation i "
            + "JOIN FETCH i.standardVersion "
            + "JOIN FETCH i.implementationCategory "
            + "JOIN FETCH i.organization "
            + "WHERE i.implementationCategory.category='APIService'";
        if (serviceType != null) {
            q += " AND i.standardVersion.standard.artifact = '" + serviceType.getArtifact() + "'";
            q += " AND i.standardVersion.versionNumber = '" + serviceType.getVersion() + "'";
        }

        return new HibernateQuerier<>(Implementation.class, q).query();
    }

    @Operation(summary = "Get service by Id",
               description = "Get all details about a single service")
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = Implementation.class),
                examples = {
                    @ExampleObject(
                        name = "Service",
                        summary = "Service",
                        value = Example.SERVICE
                    )
                }
            )
        }
    )
    @ApiResponseNotFound
    @ApiResponseServerError
    @GetMapping(path = "/{serviceId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Implementation getServiceById(
        @PathVariable("serviceId") String serviceId) {
        
        String queryString = 
            "select i from Implementation i "
            + "JOIN FETCH i.standardVersion "
            + "JOIN FETCH i.implementationCategory "
            + "JOIN FETCH i.organization "
            + "WHERE i.id='" + serviceId + "'";
        HibernateQuerier<Implementation> querier =
            new HibernateQuerier<>(Implementation.class, queryString);
        List<Implementation> implementations = querier.query();
        if (implementations.size() < 1) {
            throw new ResourceNotFoundException(
                "No Service with id: " + serviceId);
        }
        return implementations.get(0);
    }

    @Operation(summary = "List service types",
               description = "Get list of all unique service types in the registry")    
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = ServiceType.class),
                examples = {
                    @ExampleObject(
                        name = "Service Type list",
                        summary = "Service Type list",
                        value = Example.TYPE_LIST
                    )
                }
            )
        }
    )
    @ApiResponseServerError
    @GetMapping(path = "/types",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<ServiceType> getServiceTypes() {
        
        String queryString = 
            "select i from Implementation i "
            + "JOIN FETCH i.standardVersion "
            + "JOIN FETCH i.implementationCategory "
            + "JOIN FETCH i.organization "
            + "WHERE i.implementationCategory.category='APIService'";
        HibernateQuerier<Implementation> querier =
            new HibernateQuerier<>(Implementation.class, queryString);
        List<Implementation> implementations = querier.query();
        Set<ServiceType> serviceTypes = new HashSet<>();
        for (Implementation implementation: implementations) {
            serviceTypes.add(implementation.getServiceType());
        }
        return serviceTypes;
    }
}
