package org.ga4gh.registry.testutils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeaderSets {

    public static HttpHeaders ok() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "bearer mysecret");
        return headers;
    }

    public static HttpHeaders noAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    public static HttpHeaders authTokenMalformed() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "not bearer token");
        return headers;
    }

    public static HttpHeaders authTokenInvalid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "bearer invalidtoken");
        return headers;
    }
}