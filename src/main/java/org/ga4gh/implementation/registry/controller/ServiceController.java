package org.ga4gh.implementation.registry.controller;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ga4gh.implementation.registry.entity.Implementation;
import org.ga4gh.implementation.registry.util.HibernateUtil;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getServices() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String output = null;

        try {
            session.beginTransaction();
            Query<Implementation> query = session.createQuery(
                "select i from Implementation i "
                + "JOIN FETCH i.standardVersion "
                + "JOIN FETCH i.implementationCategory "
                + "JOIN FETCH i.organization",
                Implementation.class);
            List<Implementation> implementations = query.getResultList();
            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(implementations);
            session.getTransaction().commit();
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON " + e);
        } finally {
            session.close();
        }
        return output;
    }
}