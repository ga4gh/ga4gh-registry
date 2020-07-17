package org.ga4gh.registry.controller;

import java.util.Map;

import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-info")
public class ServiceInfo {

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_SERVICE_INFO_HANDLER_FACTORY)
    RequestHandlerFactory<Implementation> showServiceInfo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getServiceInfo(@RequestHeader Map<String, String> headers) {
        return showServiceInfo.handleRequest();
    }
}
