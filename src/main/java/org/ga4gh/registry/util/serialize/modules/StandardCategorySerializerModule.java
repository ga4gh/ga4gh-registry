package org.ga4gh.registry.util.serialize.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.model.StandardCategory;
import org.ga4gh.registry.util.serialize.serializers.StandardCategorySerializer;

public class StandardCategorySerializerModule extends SimpleModule {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String NAME = "StandardCategorySerializerModule";

    public StandardCategorySerializerModule() {
        super(NAME);
        addSerializer(StandardCategory.class, new StandardCategorySerializer());
    }
}