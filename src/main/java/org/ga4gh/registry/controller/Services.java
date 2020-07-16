package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.delete.DeleteServiceHandlerFactory;
import org.ga4gh.registry.util.requesthandler.post.PostServiceHandlerFactory;
import org.ga4gh.registry.util.requesthandler.put.PutServiceHandlerFactory;
import org.ga4gh.registry.util.response.factory.GetServiceByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceTypesResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServicesResponseEntityCreatorFactory;
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

@RestController
@RequestMapping("/services")
public class Services {

    @Autowired
    GetServicesResponseEntityCreatorFactory getServices;

    @Autowired
    GetServiceByIdResponseEntityCreatorFactory getServiceById;

    @Autowired
    GetServiceTypesResponseEntityCreatorFactory getServiceTypes;

    @Autowired
    PostServiceHandlerFactory postService;

    @Autowired
    PutServiceHandlerFactory putService;

    @Autowired
    DeleteServiceHandlerFactory deleteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServices(@RequestParam Map<String, String> queryVariables) {
        return getServices.createResponseEntity(queryVariables);
    }

    @GetMapping(path = "/{serviceId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceById(@PathVariable Map<String, String> pathVariables) {
        return getServiceById.createResponseEntity(pathVariables);
    }

    @GetMapping(path = "/types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceTypes() {
        return getServiceTypes.createResponseEntity();
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
}
