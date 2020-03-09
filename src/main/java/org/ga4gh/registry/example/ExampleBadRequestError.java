package org.ga4gh.registry.example;

import org.ga4gh.registry.constant.HttpStatus;

public class ExampleBadRequestError {

    public static final String EXAMPLE = "{"
    + "\"status\":" + HttpStatus.BAD_REQUEST + ","
    + "\"message\":\"Bad request\""
    + "}";

}