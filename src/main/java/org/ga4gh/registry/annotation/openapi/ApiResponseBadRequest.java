package org.ga4gh.registry.annotation.openapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ga4gh.registry.example.Example;
import org.ga4gh.registry.model.RegistryError;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiResponse(
    responseCode = HttpStatus.BAD_REQUEST,
    description = HttpStatusDescription.BAD_REQUEST,
    content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
            schema = @Schema(implementation = RegistryError.class),
            examples = {
                @ExampleObject(
                    name = "Bad Request Error",
                    summary = "Bad Request Error",
                    value = Example.BAD_REQUEST_ERROR
                )
            }
        )
    }
)
public @interface ApiResponseBadRequest {}
