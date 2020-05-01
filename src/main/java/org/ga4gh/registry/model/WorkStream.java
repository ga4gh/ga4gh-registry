package org.ga4gh.registry.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="work_stream")
public class WorkStream {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
                      strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "abbreviation")
    private String abbreviation;

    @OneToMany(mappedBy = "workStream",
               cascade=CascadeType.ALL)
    private List<Standard> standards;

    /* constructors */

    public WorkStream() {

    }

    public WorkStream(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    /* getters and setters */

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public String toString() {
        return "WorkStream [id=" + id
            + ", name=" + name 
            + ", abbreviation=" + abbreviation + "]";
    }
}