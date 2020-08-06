package org.ga4gh.registry.util.serialize;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;

import org.ga4gh.registry.constant.Ids;

public class RegistrySerializerModuleHelper {

    private static final int major = 1;
    private static final int minor = 0;
    private static final int patchLevel = 0;
    private static final String snapshotInfo = "rc";
    private static final String groupId = Ids.GA4GH_ORG_ID;

    public static Version newVersion(String artifactId) {
        return new Version(major, minor, patchLevel, snapshotInfo, groupId, artifactId);
    }

    public static List<JsonSerializer<?>> newSerializers(JsonSerializer<?>[] serializers) {
        List<JsonSerializer<?>> serializersList = new ArrayList<>();
        for (JsonSerializer<?> serializer: serializers) {
            serializersList.add(serializer);
        }
        return serializersList;
    }
}
