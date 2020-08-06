package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="work_stream")
public class WorkStream implements RegistryModel {

    public static final String tableName = "work_stream";

    @Id
    @Column(name = "id")
    private String id;

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

    public void lazyLoad() {

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