package org.ga4gh.registry.controller;

import java.util.List;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.ga4gh.registry.example.Example;
import org.ga4gh.registry.util.HibernateQuerier;
import org.ga4gh.registry.model.RegistryError;
import org.ga4gh.registry.model.Organization;
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
@RequestMapping("/organizations")
public class Organizations {

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
                            summary = "Organization list",
                            value = Example.ORGANIZATION_LIST
                        )
                    })
                }
            ),
            @ApiResponse(
                responseCode = HttpStatus.SERVER_ERROR,
                description = HttpStatusDescription.SERVER_ERROR,
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                        schema = @Schema(implementation = RegistryError.class),
                        examples = {
                            @ExampleObject(
                                name = "Server error",
                                summary = "Server error",
                                value = Example.INTERNAL_SERVER_ERROR
                            )
                        }
                    )
                }
            )       
        }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Organization> getOrganizations() {
        String q = "select o from Organization o";            
        List<Organization> orgs = new HibernateQuerier<>(Organization.class, q).query();
        System.out.println("Got the orgs");
        // System.out.println(orgs.get(0).getImplementations());
        // return null;
        return orgs;
    }

    @GetMapping(path = "/{organizationId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Organization getOrganizationById(
        @PathVariable("organizationId") String organizationId) {
        
        String q = "select o from Organization o "
                   + "JOIN FETCH o.implementations "
                   + "WHERE o.id='" + organizationId + "'";
        List<Organization> organizations = new HibernateQuerier<>(Organization.class, q).query();
        return organizations.get(0);
    }
}
