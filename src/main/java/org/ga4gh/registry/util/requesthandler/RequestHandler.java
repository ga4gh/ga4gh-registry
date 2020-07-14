package org.ga4gh.registry.util.requesthandler;

import org.ga4gh.registry.model.RegistryModel;
import org.springframework.http.ResponseEntity;

public interface RequestHandler<T extends RegistryModel> {

    public ResponseEntity<String> createResponseEntity();
}
