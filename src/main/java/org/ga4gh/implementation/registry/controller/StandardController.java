package org.ga4gh.implementation.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.ga4gh.implementation.registry.entity.Standard;
import org.ga4gh.implementation.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standards")
public class StandardController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getStandards() {

        String output = null;
        try {
            String queryString = 
            "select s from Standard s "
            + "JOIN FETCH s.standardCategory "
            + "JOIN FETCH s.standardStatus "
            + "JOIN FETCH s.standardVersions";
            HibernateQuerier<Standard> querier =
                new HibernateQuerier<>(Standard.class, queryString);
            List<Standard> implementations = querier.query();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(implementations);
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        }
        return output;
    }
}
