package org.ga4gh.implementation.registry.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ga4gh.implementation.registry.entity.Implementation;
import org.ga4gh.implementation.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-info")
public class ServiceInfoController {

    private final String uuid = "7893404d-7b73-4983-9891-89023c8a34fa";

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceInfo() {
        
        String output = null;
        try {
            String queryString =
                "select i from Implementation i "
                + "JOIN FETCH i.standardVersion "
                + "JOIN FETCH i.organization "
                + "WHERE i.id='" + uuid + "'";
            HibernateQuerier<Implementation> querier =
                new HibernateQuerier<>(Implementation.class, queryString);
            List<Implementation> implementations = querier.query();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(implementations);
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        }

        return output;
    }
}