package org.ga4gh.registry.controller;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.util.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.put.PutOrganizationHandlerFactory;
import org.ga4gh.registry.util.response.ResponseMapper;
import org.ga4gh.registry.util.response.factory.GetOrganizationByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetOrganizationsResponseEntityCreatorFactory;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    PutOrganizationHandlerFactory putOrganization;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizations() {
        return getOrganizations.createResponseEntity();
    }

    @GetMapping(path = "/{organizationId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizationById(@PathVariable Map<String, String> pathVariables) {
        return getOrganizationById.createResponseEntity(pathVariables);
    }

    @PutMapping(path = "/{organizationId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateOrganizationById(@RequestBody Organization organization, @PathVariable Map<String, String> pathVariables) {
        return putOrganization.handleRequest(pathVariables, organization);
        /*
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,PUT,POST,DELETE");
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        */
    }
}