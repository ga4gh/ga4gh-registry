package org.ga4gh.registry.util;

public class StaticUtils {

    public static String addTrailingSlash(String url) {
        String newURL = url;
        if (!newURL.endsWith("/")) {
            newURL += "/";
        } else {
        }
        return newURL;
    }
    
}
