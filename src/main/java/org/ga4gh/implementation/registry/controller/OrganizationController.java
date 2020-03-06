package org.ga4gh.implementation.registry.controller;

import java.util.List;
import org.ga4gh.implementation.registry.constant.HttpStatus;
import org.ga4gh.implementation.registry.constant.HttpStatusDescription;
import org.ga4gh.implementation.registry.util.HibernateQuerier;
import org.ga4gh.implementation.registry.model.Organization;
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
@RequestMapping("/organizations")
public class OrganizationController {

    @Operation(
        summary = "List organizations",
        description = "Get list of organizations implementing GA4GH standards",
        responses = {
            @ApiResponse(
                responseCode = HttpStatus.OK,
                description = HttpStatusDescription.OK,
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                        schema = @Schema(implementation = Organization.class),
                        examples = {
                        @ExampleObject(
                            name = "Organization list",
                            summary = "List of organizations",
                            value = "[{\"name\": \"Global Alliance for Genomics and Health\"}]"
                        )
                    })
                }
            ),
            @ApiResponse(
                responseCode = HttpStatus.SERVER_ERROR,
                description = HttpStatusDescription.SERVER_ERROR
            )       
        }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Organization> getOrganizations() {
        List<Organization> organizations;
        String queryString = 
            "select o from Organization o "
            + "JOIN FETCH o.implementations";
        HibernateQuerier<Organization> querier =
            new HibernateQuerier<>(Organization.class, queryString);
        organizations = querier.query();
        return organizations;
    }
}