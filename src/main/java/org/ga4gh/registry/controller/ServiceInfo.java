package org.ga4gh.registry.controller;

import org.ga4gh.registry.util.response.factory.GetServiceInfoResponseEntityCreatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-info")
public class ServiceInfo {

    @Autowired
    GetServiceInfoResponseEntityCreatorFactory getServiceInfo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceInfo() {
        return getServiceInfo.createResponseEntity();
    }
}
