package org.ga4gh.registry.util.response.factory;

import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.serialize.modules.ReleaseStatusSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardCategorySerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardSerializerModule;

public class GetStandardsResponseCreatorFactory extends ResponseCreatorFactory<Standard> {

    public GetStandardsResponseCreatorFactory(Class<Standard> typeClass) {
        super(typeClass);
    }

    public void load() {
        this.joinData("standardCategory")
            .joinData("releaseStatus")
            .joinData("standardVersions")
            .joinData("workStream")
            .addModule(new StandardSerializerModule())
            .addModule(new StandardCategorySerializerModule())
            .addModule(new ReleaseStatusSerializerModule());
    }
}