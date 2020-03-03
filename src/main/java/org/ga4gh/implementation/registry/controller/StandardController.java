package org.ga4gh.implementation.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.ga4gh.implementation.registry.entity.Standard;
import org.ga4gh.implementation.registry.util.HibernateUtil;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standards")
public class StandardController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getStandards() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String output = null;

        try {
            session.beginTransaction();
            Query<Standard> query = session.createQuery(
                "select s from Standard s "
                + "JOIN FETCH s.standardCategory "
                + "JOIN FETCH s.standardStatus ",
                // + "JOIN FETCH s.standardVersions",
                Standard.class);
            List<Standard> standards = query.getResultList();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(standards);
            session.getTransaction().commit();
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        } finally {
            session.close();
        }
        return output;
    }
}
