package org.ga4gh.registry.controller;

import org.ga4gh.registry.annotation.openapi.ApiResponseServerError;
import org.ga4gh.registry.constant.HttpStatus;
import org.ga4gh.registry.constant.HttpStatusDescription;
import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.example.Example;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.QuerySerializerBuilder;
import org.ga4gh.registry.util.serialize.modules.ImplementationShallowSerializerModule;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/service-info")
public class ServiceInfo {

    @Operation(
        summary = "Get Service Info",
        description = "Get all details about this service")
    @ApiResponse(
        responseCode = HttpStatus.OK,
        description = HttpStatusDescription.OK,
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                schema = @Schema(implementation = Implementation.class),
                examples = {
                    @ExampleObject(
                        name = "Service Info",
                        summary = "Service Info",
                        value = Example.SERVICE
                    )
                }
            )
        }
    )
    @ApiResponseServerError
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceInfo() {
    
        return new QuerySerializerBuilder<>(Implementation.class)
            .build()
            .join("standardVersion")
            .join("organization")
            .filter("id", Ids.SELF_UUID)
            .singleResult()
            .addModule(new ImplementationShallowSerializerModule())
            .queryAndSerialize();
    }
}
