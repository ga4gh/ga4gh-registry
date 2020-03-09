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
    responseCode = HttpStatus.NOT_FOUND,
    description = HttpStatusDescription.NOT_FOUND,
    content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
            schema = @Schema(implementation = RegistryError.class),
            examples = {
                @ExampleObject(
                    name = "Not Found Error",
                    summary = "Not Found Error",
                    value = Example.NOT_FOUND_ERROR
                )
            }
        )
    }
)
public @interface ApiResponseNotFound {}
