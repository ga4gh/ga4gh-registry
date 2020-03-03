package org.ga4gh.implementation.registry.entity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ga4gh.implementation.registry.util.serializer.OrganizationSerializer;

@Entity
@Table(name = "organization")
@JsonSerialize(using = OrganizationSerializer.class)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "organization",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<Implementation> implementations;

    /* constructors */

    public Organization() {
        
    }

    public Organization(String name, String shortName, String url) {
        this.name = name;
        this.shortName = shortName;
        this.url = url;
    }

    /* getters and setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Implementation> getImplementations() {
        return implementations;
    }

    public void setImplementations(List<Implementation> implementations) {
        this.implementations = implementations;
    }

    /* toString method */

    public String toString() {
        return "Organization [id=" + id + ", name=" + name +
               ", shortName=" + shortName + ", url=" + url + "]";
    }
}