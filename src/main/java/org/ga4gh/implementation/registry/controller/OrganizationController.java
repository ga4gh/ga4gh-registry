package org.ga4gh.implementation.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.ga4gh.implementation.registry.util.HibernateUtil;
import org.ga4gh.implementation.registry.entity.Organization;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getOrganizations() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String output = null;

        try {
            session.beginTransaction();
            Query<Organization> query = session.createQuery(
                "select o from Organization o "
                + "JOIN FETCH o.implementations",
                Organization.class
            );
            List<Organization> organizations = query.getResultList();

            System.out.println(organizations.get(0).toString());

            ObjectMapper mapper = new ObjectMapper();
            output = mapper.writeValueAsString(organizations);
            session.getTransaction().commit();
        } catch (IOException e) {
            System.out.println("Could not serialize object as JSON");
        } finally {
            session.close();
        }
        return output;
    }
}