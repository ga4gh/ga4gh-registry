package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;

@EnableWebMvc
@RestController
@RequestMapping("/standards")
public class Standards {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> indexStandard;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> showStandard;

    @Autowired
    @Qualifier(AppConfigConstants.POST_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> postStandard;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> putStandard;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> deleteStandard;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandards() {
        return indexStandard.handleRequest();
    }

    @GetMapping(path = "/{standardId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandardById(@PathVariable Map<String, String> pathVariables) {
        return showStandard.handleRequest(pathVariables);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createStandard(@RequestBody Standard standard) {
        return postStandard.handleRequest(standard);
    }

    @PutMapping(path = "/{standardId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateStandardById(@PathVariable Map<String, String> pathVariables, @RequestBody Standard standard) {
        return putStandard.handleRequest(pathVariables, standard);
    }

    @DeleteMapping(path = "/{standardId:.+}")
    public ResponseEntity<String> deleteStandardById(@PathVariable Map<String, String> pathVariables) {
        return deleteStandard.handleRequest(pathVariables);
    }
}
