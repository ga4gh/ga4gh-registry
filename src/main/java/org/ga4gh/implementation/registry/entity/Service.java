package org.ga4gh.implementation.registry.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
@Entity
@Table(name="services")
@EntityListeners(AuditingEntityListener.class)
public class Service extends Implementation {

    private String id;
    private String name;
    private ImplementationType type;
    private String description;
    private Organization organization;
    private String contactUrl;
    private String documentationUrl;
    private String createdAt;
    private String updatedAt;
    private String environment;
    private String version;
}

*/