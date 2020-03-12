package org.ga4gh.registry.controller;

import org.ga4gh.registry.util.QuerySerializerBuilder;
import org.ga4gh.registry.util.serialize.modules.ImplementationShallowSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationDeepSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationShallowSerializerModule;
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
        return new QuerySerializerBuilder<>(Organization.class)
            .build()
            .addModule(new OrganizationShallowSerializerModule())
            .queryAndSerialize();
    }

    @GetMapping(path = "/{organizationId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getOrganizationById(
        @PathVariable("organizationId") String organizationId) {
        
        return new QuerySerializerBuilder<>(Organization.class)
            .build()
            .join("implementations")
            .filter("id", organizationId)
            .singleResult()
            .addModule(new OrganizationDeepSerializerModule())
            .addModule(new ImplementationShallowSerializerModule())
            .queryAndSerialize();
    }
}
