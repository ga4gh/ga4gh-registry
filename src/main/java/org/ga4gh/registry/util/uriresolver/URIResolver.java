package org.ga4gh.registry.util.uriresolver;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.StaticUtils;

public class URIResolver {

    public String drsv1(Implementation service, String id) {
        String resolvedURL = StaticUtils.addTrailingSlash(service.getUrl()) + "ga4gh/drs/v1/objects/" + id;
        return "{\"resolvedURL\":\"" + resolvedURL + "\"}";
    }
    
}
