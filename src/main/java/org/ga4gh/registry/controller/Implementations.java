package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/implementations")
public class Implementations {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> indexImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> showImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.POST_IMPLEMENTATION_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> postImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_IMPLEMENTATION_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> putImplementation;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> deleteImplementation;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getImplementations(@RequestParam Map<String, String> queryVariables) {
        return indexImplementation.handleRequest(queryVariables);
    }

    @GetMapping(path = "/{implementationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getImplementationById(@PathVariable Map<String, String> pathVariables) {
        return showImplementation.handleRequest(pathVariables);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createImplementation(@RequestBody Implementation implementation) {
        return postImplementation.handleRequest(implementation);
    }

    @PutMapping(path = "/{implementationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateImplementationById(@PathVariable Map<String, String> pathVariables, @RequestBody Implementation implementation) {
        return putImplementation.handleRequest(pathVariables, implementation);
    }

    @DeleteMapping(path = "/{implementationId:.+}")
    public ResponseEntity<String> deleteImplementationById(@PathVariable Map<String, String> pathVariables) {
        return deleteImplementation.handleRequest(pathVariables);
    }
}
