package org.ga4gh.registry.util.response.factory;

import java.util.HashMap;
import java.util.Map;

import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.response.ResponseCreator;
import org.ga4gh.registry.util.serialize.modules.ReleaseStatusSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardCategorySerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardVersionSerializerModule;

public class GetStandardByIdResponseCreatorFactory extends ResponseCreatorFactory<Standard> {

    public GetStandardByIdResponseCreatorFactory(Class<Standard> typeClass) {
        super(typeClass);
    }

    public void load() {
        HashMap<String, Boolean> serializeStandard = new HashMap<>();
        serializeStandard.put("description", true);
        serializeStandard.put("versions", true);

        this.joinData("standardCategory")
            .joinData("releaseStatus")
            .joinData("standardVersions")
            .singleResult()
            .addModule(new StandardSerializerModule(serializeStandard))
            .addModule(new StandardVersionSerializerModule())
            .addModule(new StandardCategorySerializerModule())
            .addModule(new ReleaseStatusSerializerModule());
    }

    public void prepareResponseCreator(
        ResponseCreator<Standard> responseCreator,
        Map<String, String> pathVariables) {
        responseCreator
            .getHibernateQueryBuilder()
            .filter("id", pathVariables.get("standardId"));
    }
}