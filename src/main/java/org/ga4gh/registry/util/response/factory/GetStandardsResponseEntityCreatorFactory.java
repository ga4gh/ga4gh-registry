package org.ga4gh.registry.util.response.factory;

import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.serialize.modules.ReleaseStatusSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardCategorySerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardSerializerModule;

public class GetStandardsResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Standard> {

    public GetStandardsResponseEntityCreatorFactory(Class<Standard> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    public void loadFactory() {
        addDataJoin("standardCategory");
        addDataJoin("releaseStatus");
        addDataJoin("standardVersions");
        addDataJoin("workStream");
        addSerializerModule(new StandardSerializerModule());
        addSerializerModule(new StandardCategorySerializerModule());
        addSerializerModule(new ReleaseStatusSerializerModule());
    }
}