package org.ga4gh.registry.annotation.openapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Parameter(
    in = ParameterIn.QUERY,
    name = "type",
    description = "Filter services by GA4GH service type. Formatted according to GA4GH service-info specification 'type' parameter, serialized as ${GROUP}:${ARTIFACT}:${VERSION}",
    example = "org.ga4gh:service-registry:1.0.0",
    schema = @Schema(implementation = String.class))
public @interface ParameterType {}