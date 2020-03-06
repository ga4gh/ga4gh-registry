package org.ga4gh.registry.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.HibernateQuerier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-info")
public class ServiceInfoController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServiceInfo() {
        
        String output = null;
        try {
            String queryString =
                "select i from Implementation i "
                + "JOIN FETCH i.standardVersion "
                + "JOIN FETCH i.organization "
                + "WHERE i.id='" + Ids.SELF_UUID + "'";
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