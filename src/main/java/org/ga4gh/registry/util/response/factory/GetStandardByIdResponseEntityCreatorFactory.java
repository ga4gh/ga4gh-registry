package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.ga4gh.registry.util.serialize.modules.ReleaseStatusSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardCategorySerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardVersionSerializerModule;
import org.ga4gh.registry.util.serialize.modules.WorkStreamSerializerModule;

public class GetStandardByIdResponseEntityCreatorFactory extends AbstractResponseEntityCreatorFactory<Standard> {

    public GetStandardByIdResponseEntityCreatorFactory(Class<Standard> responseEntityCreatorTypeClass, String responseEntityCreatorBeanName) {
        super(responseEntityCreatorTypeClass, responseEntityCreatorBeanName);
    }

    public void loadFactory() {
        HashMap<String, Boolean> serializeStandard = new HashMap<>();
        serializeStandard.put("description", true);
        serializeStandard.put("versions", true);
        serializeStandard.put("workStream", true);

        addDataJoin("standardCategory");
        addDataJoin("releaseStatus");
        addDataJoin("standardVersions");
        addDataJoin("workStream");
        setReturnSingleResult(true);
        addSerializerModule(new StandardSerializerModule(serializeStandard));
        addSerializerModule(new StandardVersionSerializerModule());
        addSerializerModule(new StandardCategorySerializerModule());
        addSerializerModule(new ReleaseStatusSerializerModule());
        addSerializerModule(new WorkStreamSerializerModule());
    }

    public void prepareResponseEntityCreator(ResponseEntityCreator<Standard> responseEntityCreator, Map<String, String> pathVariables) {
        responseEntityCreator
            .getHibernateQueryBuilder()
            .filter("id", pathVariables.get("standardId"));
    }
}