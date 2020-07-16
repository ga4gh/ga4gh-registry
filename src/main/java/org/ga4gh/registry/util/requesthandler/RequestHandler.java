package org.ga4gh.registry.util.requesthandler;

import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.RegistryModel;
import org.springframework.http.ResponseEntity;

public interface RequestHandler<T extends RegistryModel> {
    public T preProcessRequestBody(T requestBody) throws ResourceNotFoundException;
    public ResponseEntity<String> createResponseEntity();
}
