package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "implementation_category")
public class ImplementationCategory implements RegistryModel {

    private static final String tableName = "implementation_category";

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy="implementationCategory",
               cascade={CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH})
    private List<Implementation> implementations;

    public ImplementationCategory() {

    }

    public ImplementationCategory(String category) {
        this.category = category;
    }

    public void lazyLoad() {
        
    }

    public String getTableName() {
        return tableName;
    }

    public String getId() {
        return this.id;
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

    public List<Implementation> getImplementations() {
        return implementations;
    }

    public void setImplementations(List<Implementation> implementations) {
        this.implementations = implementations;
    }

    public String toString() {
        return "ImplementationCategory [id=" + id 
            + ", category=" + category + "]"; 
    }
}