package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.ga4gh.registry.util.response.factory.GetOrganizationByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetOrganizationsResponseEntityCreatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/organizations")
public class Organizations {

    @Autowired
    GetOrganizationsResponseEntityCreatorFactory getOrganizations;

    @Autowired
    GetOrganizationByIdResponseEntityCreatorFactory getOrganizationById;

    @Autowired
    @Qualifier("postOrganizationHandlerFactory")
    RequestHandlerFactory<Organization> postOrganization;

    @Autowired
    @Qualifier("putOrganizationHandlerFactory")
    RequestHandlerFactory<Organization> putOrganization;

    @Autowired
    @Qualifier("deleteOrganizationHandlerFactory")
    RequestHandlerFactory<Organization> deleteOrganization;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizations() {
        return getOrganizations.createResponseEntity();
    }

    @GetMapping(path = "/{organizationId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizationById(@PathVariable Map<String, String> pathVariables) {
        return getOrganizationById.createResponseEntity(pathVariables);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization) {
        return postOrganization.handleRequest(organization);
    }

    @PutMapping(path = "/{organizationId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateOrganizationById(@PathVariable Map<String, String> pathVariables, @RequestBody Organization organization) {
        return putOrganization.handleRequest(pathVariables, organization);        
    }

    @DeleteMapping(path = "/{organizationId}")
    public ResponseEntity<String> deleteOrganizationById(@PathVariable Map<String, String> pathVariables) {
        return deleteOrganization.handleRequest(pathVariables);
    }   
}