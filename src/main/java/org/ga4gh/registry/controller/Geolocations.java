package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Geolocation;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.ResponseEntity;

@EnableWebMvc
@RestController
@RequestMapping("/geolocations")
public class Geolocations {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_GEOLOCATION_HANDLER_FACTORY)
    RequestHandlerFactory<Geolocation> indexGeolocation;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_GEOLOCATION_HANDLER_FACTORY)
    RequestHandlerFactory<Geolocation> showGeolocation;

    @Autowired
    @Qualifier(AppConfigConstants.POST_GEOLOCATION_HANDLER_FACTORY)
    RequestHandlerFactory<Geolocation> postGeolocation;

    @Autowired
    @Qualifier(AppConfigConstants.PUT_GEOLOCATION_HANDLER_FACTORY)
    RequestHandlerFactory<Geolocation> putGeolocation;

    @Autowired
    @Qualifier(AppConfigConstants.DELETE_GEOLOCATION_HANDLER_FACTORY)
    RequestHandlerFactory<Geolocation> deleteGeolocation;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getGeolocations() {
        return indexGeolocation.handleRequest();
    }

    @GetMapping(path = "/{geolocationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getGeolocationById(@PathVariable Map<String, String> pathVariables) {
        return showGeolocation.handleRequest(pathVariables);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createGeolocation(@RequestBody Geolocation geolocation) {
        return postGeolocation.handleRequest(geolocation);
    }

    @PutMapping(path = "/{geolocationId:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateGeolocationById(@PathVariable Map<String, String> pathVariables, @RequestBody Geolocation geolocation) {
        return putGeolocation.handleRequest(pathVariables, geolocation);        
    }

    @DeleteMapping(path = "/{geolocationId:.+}")
    public ResponseEntity<String> deleteGeolocationById(@PathVariable Map<String, String> pathVariables) {
        return deleteGeolocation.handleRequest(pathVariables);
    }   
}
