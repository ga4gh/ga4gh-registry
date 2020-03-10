package org.ga4gh.registry.controller;

import java.util.List;
import org.ga4gh.registry.annotation.openapi.ApiResponseServerError;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.example.Example;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/service-info")
public class ServiceInfo {

    @Operation(
        summary = "Get Service Info",
        description = "Get all details about this service")
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = Implementation.class),
                examples = {
                    @ExampleObject(
                        name = "Service Info",
                        summary = "Service Info",
                        value = Example.SERVICE
                    )
                }
            )
        }
    )
    @ApiResponseServerError
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Implementation getServiceInfo() {
        
        String queryString =
            "select i from Implementation i "
            + "JOIN FETCH i.standardVersion "
            + "JOIN FETCH i.organization "
            + "WHERE i.id='" + Ids.SELF_UUID + "'";
        HibernateQuerier<Implementation> querier =
            new HibernateQuerier<>(Implementation.class, queryString);
        List<Implementation> implementations = querier.query();
        return implementations.get(0);
    }
}
