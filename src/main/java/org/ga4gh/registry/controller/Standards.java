package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.util.response.factory.GetStandardByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetStandardsResponseEntityCreatorFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/standards")
public class Standards {

    @Autowired
    GetStandardsResponseEntityCreatorFactory getStandards;

    @Autowired
    GetStandardByIdResponseEntityCreatorFactory getStandardById;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandards() {
        return getStandards.createResponseEntity();
    }

    @GetMapping(path = "/{standardId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandardById(@PathVariable Map<String, String> pathVariables) {
        return getStandardById.createResponseEntity(pathVariables);
    }
}
