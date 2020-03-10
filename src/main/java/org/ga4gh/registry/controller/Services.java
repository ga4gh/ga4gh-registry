package org.ga4gh.registry.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.ga4gh.registry.annotation.openapi.ApiResponseBadRequest;
import org.ga4gh.registry.annotation.openapi.ApiResponseNotFound;
import org.ga4gh.registry.annotation.openapi.ApiResponseServerError;
import org.ga4gh.registry.annotation.openapi.ParameterType;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.ga4gh.registry.example.Example;
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
