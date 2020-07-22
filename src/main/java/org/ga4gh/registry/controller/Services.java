package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@RestController
@RequestMapping("/services")
public class Services {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_SERVICE_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> indexService;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_SERVICE_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> showService;

    @Autowired
    @Qualifier(AppConfigConstants.POST_SERVICE_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> postService;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_SERVICE_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> putService;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_SERVICE_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> deleteService;

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> indexServiceType;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServices(@RequestParam Map<String, String> queryVariables) {
        return indexService.handleRequest(queryVariables);
    }

    @GetMapping(path = "/{serviceId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceById(@PathVariable Map<String, String> pathVariables) {
        return showService.handleRequest(pathVariables);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createService(@RequestBody Implementation implementation) {
        return postService.handleRequest(implementation);
    }

    @PutMapping(path = "/{serviceId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateServiceById(@PathVariable Map<String, String> pathVariables, @RequestBody Implementation implementation) {
        return putService.handleRequest(pathVariables, implementation);
    }

    @DeleteMapping(path = "/{serviceId}")
    public ResponseEntity<String> deleteServiceById(@PathVariable Map<String, String> pathVariables) {
        return deleteService.handleRequest(pathVariables);
    }

    @GetMapping(path = "/types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceTypes() {
        return indexServiceType.handleRequest();
    }
}
