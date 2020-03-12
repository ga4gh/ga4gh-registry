package org.ga4gh.registry.controller;

import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.response.ResponseCreatorBuilder;
import org.ga4gh.registry.util.serialize.modules.ReleaseStatusSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardCategorySerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardShallowSerializerModule;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standards")
public class Standards {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getStandards() {

        return new ResponseCreatorBuilder<>(Standard.class)
            .joinData("standardCategory")
            .joinData("releaseStatus")
            .joinData("standardVersions")
            .addModule(new StandardShallowSerializerModule())
            .addModule(new StandardCategorySerializerModule())
            .addModule(new ReleaseStatusSerializerModule())
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }

    @GetMapping(path = "/{standardId}",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getStandardById(@PathVariable("standardId") String standardId) {
        return new ResponseCreatorBuilder<>(Standard.class)
            .joinData("standardCategory")
            .joinData("releaseStatus")
            .joinData("standardVersions")
            .filterData("id", standardId)
            .singleResult()
            .addModule(new StandardShallowSerializerModule())
            .addModule(new StandardCategorySerializerModule())
            .addModule(new ReleaseStatusSerializerModule())
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }
}
