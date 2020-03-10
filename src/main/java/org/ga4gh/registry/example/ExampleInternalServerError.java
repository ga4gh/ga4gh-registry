package org.ga4gh.registry.example;

import org.ga4gh.registry.constant.HttpStatus;

public class ExampleInternalServerError {

    public static final String EXAMPLE = "{"
        + "\"status\":" + HttpStatus.INTERNAL_SERVER_ERROR + ","
        + "\"message\":\"Internal server error\""
        + "}";
}