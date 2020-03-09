package org.ga4gh.registry.example;

import org.ga4gh.registry.constant.Ids;

public class ExampleService {

    public static final String EXAMPLE = "{"
        + "\"id\":\"" + Ids.SELF_UUID + "\","
        + "\"name\":\"GA4GH Standards and Implementations Registry\","
        + "\"description\":\"Flexible API registry of GA4GH standards and associated implementations\","
        + "\"organization\":\"" + Ids.ORGANIZATION_UUID + "\","
        + "\"contactUrl\":\"jeremy.adams@ga4gh.org\","
        + "\"documentationUrl\":\"https://ga4gh.org\","
        + "\"version\":\"1.0.0\","
        + "\"url\":\"https://registry.ga4gh.org\","
        + "\"type\":" + ExampleType.EXAMPLE
        + "}";

}