package org.ga4gh.registry.controller;

import java.util.Map;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/standards")
public class Standards {

    @Autowired
    @Qualifier(AppConfigConstants.INDEX_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> indexStandard;

    @Autowired
    @Qualifier(AppConfigConstants.SHOW_STANDARD_HANDLER_FACTORY)
    RequestHandlerFactory<Standard> showStandard;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandards() {
        return indexStandard.handleRequest();
    }

    @GetMapping(path = "/{standardId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getStandardById(@PathVariable Map<String, String> pathVariables) {
        return showStandard.handleRequest(pathVariables);
    }
}
