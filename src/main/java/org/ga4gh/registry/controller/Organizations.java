package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.util.response.factory.GetOrganizationByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetOrganizationsResponseEntityCreatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizations() {
        return getOrganizations.createResponseEntity();
    }

    @GetMapping(path = "/{organizationId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getOrganizationById(@PathVariable Map<String, String> pathVariables) {
        return getOrganizationById.createResponseEntity(pathVariables);
    }
}