package org.ga4gh.registry.util.serialize.modules;

import java.util.Map;
import org.ga4gh.registry.model.WorkStream;
import org.ga4gh.registry.util.serialize.serializers.WorkStreamSerializer;

public class WorkStreamSerializerModule extends VariableDepthSerializerModule {

    private static final long serialVersionUID = 1L;

    public WorkStreamSerializerModule() {
        super();
        addSerializer(WorkStream.class, new WorkStreamSerializer());
    }

    public WorkStreamSerializerModule(Map<String, Boolean> serializeMappedAttrs) {
        super(serializeMappedAttrs);
        addSerializer(WorkStream.class, new WorkStreamSerializer(serializeMappedAttrs));
    }

}