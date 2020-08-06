package org.ga4gh.registry.util.requesthandler;

import java.util.Map;

import org.ga4gh.registry.model.RegistryModel;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;

public interface RequestHandlerFactoryI<T extends RegistryModel> extends ApplicationContextAware {
    public ResponseEntity<String> handleRequest();
    public ResponseEntity<String> handleRequest(Map<String, String> requestVariables);
    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB);
    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, Map<String, String> requestVariablesC);
    public ResponseEntity<String> handleRequest(T requestBody);
    public ResponseEntity<String> handleRequest(Map<String, String> requestVariables, T requestBody);
    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, T requestBody);
    public ResponseEntity<String> handleRequest(Map<String, String> requestVariablesA, Map<String, String> requestVariablesB, Map<String, String> requestVariablesC, T requestBody);
}