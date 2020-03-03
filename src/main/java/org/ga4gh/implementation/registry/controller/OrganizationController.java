package org.ga4gh.implementation.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.ga4gh.implementation.registry.util.HibernateQuerier;
import org.ga4gh.implementation.registry.entity.Organization;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getOrganizations() {

        String output = null;
        try {
            String queryString = 
                "select o from Organization o "
                + "JOIN FETCH o.implementations";
            HibernateQuerier<Organization> querier =
                new HibernateQuerier<>(Organization.class, queryString);
            List<Organization> implementations = querier.query();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(implementations);
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        }
        return output;
    }
}