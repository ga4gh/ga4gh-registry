package org.ga4gh.registry.controller;

import org.ga4gh.registry.util.response.ResponseCreatorBuilder;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;
import org.ga4gh.registry.model.Organization;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
public class Organizations {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getOrganizations() {

        return new ResponseCreatorBuilder<>(Organization.class)
            .addModule(new OrganizationSerializerModule())
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }

    @GetMapping(path = "/{organizationId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getOrganizationById(
        @PathVariable("organizationId") String organizationId) {

        return new ResponseCreatorBuilder<>(Organization.class)
            .joinData("implementations")
            .filterData("id", organizationId)
            .singleResult()
            .addModule(new OrganizationSerializerModule(true))
            .addModule(new ImplementationSerializerModule())
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }
}
