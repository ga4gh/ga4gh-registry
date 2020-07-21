package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.Hibernate;

@Entity
@Table(name = "organization")
public class Organization implements RegistryModel {

    private static final String tableName = "organization";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "url")
    @NotNull
    private String url;

    @OneToMany(mappedBy = "organization",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    private List<Implementation> implementations;

    /* constructors */

    public Organization() {}

    public Organization(String name, String shortName, String url) {
        this.name = name;
        this.shortName = shortName;
        this.url = url;
    }

    public void lazyLoad() {
        Hibernate.initialize(getImplementations());
    }

    public String getTableName() {
        return tableName;
    }

    /* getters and setters */

    public String getId() {
        return id;
    }

    public void setId(String id) {
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