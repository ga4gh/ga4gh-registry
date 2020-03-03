package org.ga4gh.implementation.registry.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "implementation_category")
public class ImplementationCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy="implementationCategory",
               cascade={CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH})
    private List<Implementation> implementations;

    // private Implementation implementation;

    public ImplementationCategory() {

    }

    public ImplementationCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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