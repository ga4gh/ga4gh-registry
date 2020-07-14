package org.ga4gh.registry.util.response.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.ga4gh.registry.util.response.ResponseMapper;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponseEntityCreatorFactory<T extends RegistryModel> implements ResponseEntityCreatorFactory<T>, ApplicationContextAware {

    private ApplicationContext context;
    private Class<T> responseEntityCreatorTypeClass;
    private String responseEntityCreatorBeanNamePrefix;
    private String responseEntityCreatorBeanNameSuffix = "ResponseEntityCreator";
    private List<String> dataJoins;
    private List<String[]> dataFilters;
    private List<SimpleModule> serializerModules;
    private boolean returnSingleResult;
    private boolean performResultPostProcessing;

    public AbstractResponseEntityCreatorFactory(Class<T> responseEntityCreatorTypeClass, String responseEntityCreatorBeanNamePrefix) {
        this.responseEntityCreatorTypeClass = responseEntityCreatorTypeClass;
        this.responseEntityCreatorBeanNamePrefix = responseEntityCreatorBeanNamePrefix;
        dataJoins = new ArrayList<>();
        dataFilters = new ArrayList<>();
        serializerModules = new ArrayList<>();
        returnSingleResult = false;
        performResultPostProcessing = false;
        this.loadFactory();
    }

    @SuppressWarnings("unchecked")
    public ResponseEntityCreator<T> createNaiveResponseEntityCreator() {
        ResponseEntityCreator<T> responseEntityCreator = getContext().getBean(getResponseEntityCreatorBeanName(), ResponseEntityCreator.class);
        HibernateQueryBuilder queryBuilder = responseEntityCreator.getHibernateQueryBuilder();
        for (String dataJoin : getDataJoins()) {
            queryBuilder.join(dataJoin);
        }
        for (String[] dataFilter : getDataFilters()) {
            queryBuilder.filter(dataFilter[0], dataFilter[1]);
        }
        ResponseMapper mapper = responseEntityCreator.getResponseMapper();
        for (SimpleModule serializerModule : getSerializerModules()) {
            mapper.addModule(serializerModule);
        }
        responseEntityCreator.setSingleResult(getReturnSingleResult());
        return responseEntityCreator;
    }

    public ResponseEntity<String> downstreamCreateResponseEntity(ResponseEntityCreator<T> responseEntityCreator) {
        responseEntityCreator.buildResponseEntity();
        if (getPerformResultPostProcessing()) {
            postProcessResponse(responseEntityCreator);
        }
        return responseEntityCreator.getResponseEntity();
    }

    @Override
    public ResponseEntity<String> createResponseEntity() {
        ResponseEntityCreator<T> responseEntityCreator = createNaiveResponseEntityCreator();
        prepareResponseEntityCreator(responseEntityCreator);
        return downstreamCreateResponseEntity(responseEntityCreator);
    }

    @Override
    public ResponseEntity<String> createResponseEntity(Map<String, String> reqVarsA) {
        ResponseEntityCreator<T> responseEntityCreator = createNaiveResponseEntityCreator();
        prepareResponseEntityCreator(responseEntityCreator, reqVarsA);
        return downstreamCreateResponseEntity(responseEntityCreator);
    }

    @Override
    public ResponseEntity<String> createResponseEntity(Map<String, String> reqVarsA, Map<String, String> reqVarsB) {
        ResponseEntityCreator<T> responseEntityCreator = createNaiveResponseEntityCreator();
        prepareResponseEntityCreator(responseEntityCreator, reqVarsA, reqVarsB);
        return downstreamCreateResponseEntity(responseEntityCreator);
    }

    @Override
    public ResponseEntity<String> createResponseEntity(Map<String, String> reqVarsA, Map<String, String> reqVarsB, Map<String, String> reqVarsC) {
        ResponseEntityCreator<T> responseEntityCreator = createNaiveResponseEntityCreator();
        prepareResponseEntityCreator(responseEntityCreator, reqVarsA, reqVarsB, reqVarsC);
        return downstreamCreateResponseEntity(responseEntityCreator);
    }

    public void setResponseEntityCreatorTypeClass(Class<T> responseEntityCreatorTypeClass) {
        this.responseEntityCreatorTypeClass = responseEntityCreatorTypeClass;
    }

    public Class<T> getResponseEntityCreatorTypeClass() {
        return responseEntityCreatorTypeClass;
    }

    public void setResponseEntityCreatorBeanNamePrefix(String responseEntityCreatorBeanNamePrefix) {
        this.responseEntityCreatorBeanNamePrefix = responseEntityCreatorBeanNamePrefix;
    }

    public String getResponseEntityCreatorBeanNamePrefix() {
        return responseEntityCreatorBeanNamePrefix;
    }

    public String getResponseEntityCreatorBeanNameSuffix() {
        return responseEntityCreatorBeanNameSuffix;
    }

    public String getResponseEntityCreatorBeanName() {
        return getResponseEntityCreatorBeanNamePrefix() + getResponseEntityCreatorBeanNameSuffix();
    }

    @Override
    public void addDataJoin(String property) {
        dataJoins.add(property);
    }

    @Override
    public List<String> getDataJoins() {
        return dataJoins;
    }

    @Override
    public void addDataFilter(String property, String value) {
        String[] dataFilter = {property, value};
        dataFilters.add(dataFilter);
    }

    @Override
    public List<String[]> getDataFilters() {
        return dataFilters;
    }

    @Override
    public void addSerializerModule(SimpleModule serializerModule) {
        serializerModules.add(serializerModule);
    }

    @Override
    public List<SimpleModule> getSerializerModules() {
        return serializerModules;
    }

    @Override
    public void setReturnSingleResult(boolean set) {
        returnSingleResult = set;
    }

    @Override
    public boolean getReturnSingleResult() {
        return returnSingleResult;
    }

    @Override
    public void setPerformResultPostProcessing(boolean set) {
        performResultPostProcessing = set;
    }

    @Override
    public boolean getPerformResultPostProcessing() {
        return performResultPostProcessing;
    }

    public void loadFactory() {}

    @Override
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator) {}

    @Override
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator, Map<String, String> reqVarsA) {}

    @Override
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator, Map<String, String> reqVarsA, Map<String, String> reqVarsB) {}

    @Override
    public void prepareResponseEntityCreator(ResponseEntityCreator<T> responseEntityCreator, Map<String, String> reqVarsA, Map<String, String> reqVarsB, Map<String, String> reqVarsC) {}

    @Override
    public void postProcessResponse(ResponseEntityCreator<T> responseEntityCreator) {}

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }
}