package org.ga4gh.registry.controller;

import java.util.List;

import org.ga4gh.registry.annotation.openapi.ApiResponseNotFound;
import org.ga4gh.registry.annotation.openapi.ApiResponseServerError;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.ga4gh.registry.example.Example;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/standards")
public class Standards {

    @Operation(summary = "List standards",
               description = "Get list of GA4GH standards")
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = Standard.class),
                examples = {
                    @ExampleObject(
                        name = "Standards list",
                        summary = "Standards list",
                        value = Example.STANDARD_LIST
                    )
                }
            )
        }
    )
    @ApiResponseServerError
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Standard> getStandards() {

        String queryString = 
        "select distinct s from Standard s "
        + "JOIN FETCH s.standardCategory "
        + "JOIN FETCH s.releaseStatus "
        + "JOIN FETCH s.standardVersions";
        return new HibernateQuerier<>(Standard.class, queryString).query();
    }

    @Operation(summary = "Get standard by Id",
               description = "Get all details about a single standard")
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = Standard.class),
                examples = {
                    @ExampleObject(
                        name = "Standard",
                        summary = "Standard",
                        value = Example.STANDARD
                    )
                }
            )
        }
    )
    @ApiResponseNotFound
    @ApiResponseServerError
    @GetMapping(path = "/{standardId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Standard getStandardById(@PathVariable("standardId") String standardId) {

        String queryString = 
            "select s from Standard s "
            + "JOIN FETCH s.standardCategory "
            + "JOIN FETCH s.releaseStatus "
            + "JOIN FETCH s.standardVersions "
            + "WHERE s.id='" + standardId + "'";
        HibernateQuerier<Standard> querier =
            new HibernateQuerier<>(Standard.class, queryString);
        List<Standard> standards = querier.query();
        if (standards.size() < 1) {
            throw new ResourceNotFoundException(
                "No Standard with id: " + standardId);
        }
        return standards.get(0);
    }
}
