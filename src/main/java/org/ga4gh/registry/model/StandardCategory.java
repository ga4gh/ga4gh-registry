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

@Entity
@Table(name = "standard_category")
public class StandardCategory implements RegistryModel {

    public static final String tableName = "standard_category";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "standardCategory",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<Standard> standards;

    /* constructors */

    public StandardCategory() {

    }

    public StandardCategory(String category) {
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    /* toString method */

    public String toString() {
        return "StandardCategory [id=" + id + ", category=" + category + "]";
    }
}