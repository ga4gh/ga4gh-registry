package org.ga4gh.implementation.registry.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.ga4gh.implementation.registry.util.serializer.StandardSerializer;

@Entity
@Table(name="standard")
@JsonSerialize(using = StandardSerializer.class)
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_category_id")
    private StandardCategory standardCategory;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_status_id")
    private StandardStatus standardStatus;

    @OneToMany(mappedBy = "standard",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    private List<StandardVersion> standardVersions;

    /* constructors */

    public Standard() {

    }

    public Standard(String name, String shortDescription, 
        String longDescription, String documentationUrl) {
            this.name = name;
            this.shortDescription = shortDescription;
            this.longDescription = longDescription;
            this.documentationUrl = documentationUrl;
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

    public StandardCategory getStandardCategory() {
        return standardCategory;
    }

    public void setStandardCategory(StandardCategory standardCategory) {
        this.standardCategory = standardCategory;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public StandardStatus getStandardStatus() {
        return standardStatus;
    }

    public void setStandardStatus(StandardStatus standardStatus) {
        this.standardStatus = standardStatus;
    }

    public List<StandardVersion> getStandardVersions() {
        return standardVersions;
    }

    public void setStandardVersions(List<StandardVersion> standardVersions) {
        this.standardVersions = standardVersions;
    }

    public String toString() {
        return "Standard [id=" + id + ", name=" + name + 
               ", shortDescription=" + shortDescription +
               ", longDescription=" + longDescription +
               ", documentationUrl=" + documentationUrl + "]";
    }
}
