package org.ga4gh.registry.example;

import org.ga4gh.registry.constant.HttpStatus;

public class ExampleNotFoundError {

    public static final String EXAMPLE = "{"
    + "\"status\":" + HttpStatus.NOT_FOUND + ","
    + "\"message\":\"Specified resource not found\""
    + "}";

}