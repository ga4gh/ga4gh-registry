package org.ga4gh.registry.controller;

import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.response.ResponseCreatorBuilder;
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-info")
public class ServiceInfo {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceInfo() {

        return new ResponseCreatorBuilder<>(Implementation.class)
            .joinData("standardVersion")
            .joinData("organization")
            .filterData("id", Ids.SELF_UUID)
            .singleResult()
            .addModule(new ImplementationSerializerModule(true))
            .addModule(new OrganizationSerializerModule())
            .buildResponseCreator()
            .buildResponse()
            .getResponse();
    }
}
