package org.ga4gh.registry.example;

import org.ga4gh.registry.constant.HttpStatus;

public class ExampleInternalServerError {

    public static final String EXAMPLE = "{"
        + "\"responseCode\":" + HttpStatus.INTERNAL_SERVER_ERROR + ","
        + "\"message\":\"Internal server error\""
        + "}";

}