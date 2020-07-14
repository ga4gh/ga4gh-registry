package org.ga4gh.registry.util.response.factory;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.springframework.http.ResponseEntity;

public interface ResponseEntityCreatorFactory<T extends RegistryModel> {

    public ResponseEntity<String> createResponseEntity();
    public ResponseEntity<String> createResponseEntity(Map<String, String> reqVarsA);
    public ResponseEntity<String> createResponseEntity(Map<String, String> reqVarsA, Map<String, String> reqVarsB);
    public ResponseEntity<String> createResponseEntity(Map<String, String> reqVarsA, Map<String, String> reqVarsB, Map<String, String> reqVarsC);
    public void addDataJoin(String property);
    public List<String> getDataJoins();
    public void addDataFilter(String property, String value);
    public List<String[]> getDataFilters();
    public void addSerializerModule(SimpleModule module);
    public List<SimpleModule> getSerializerModules();
    public void setReturnSingleResult(boolean set);
    public boolean getReturnSingleResult();
    public void setPerformResultPostProcessing(boolean set);
    public boolean getPerformResultPostProcessing();
    public void loadFactory();
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator);
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator, Map<String, String> reqVarsA);
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator, Map<String, String> reqVarsA, Map<String, String> reqVarsB);
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator, Map<String, String> reqVarsA, Map<String, String> reqVarsB, Map<String, String> reqVarsC);
    public void postProcessResponse(ResponseEntityCreator<T> responseEntityCreator);
}
