package org.ga4gh.implementation.registry.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ga4gh.implementation.registry.util.serializer.StandardVersionSerializer;

@Entity
@Table(name = "standard_version")
@JsonSerialize(using = StandardVersionSerializer.class)
public class StandardVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_id")
    private Standard standard;

    @Column(name = "version_number")
    private String versionNumber;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @OneToMany(mappedBy="standardVersion",
               cascade=CascadeType.ALL)
    private List<Implementation> implementations;

    /* constructors */

    public StandardVersion() {

    }

    public StandardVersion(String versionNumber, String documentationUrl) {
        this.versionNumber = versionNumber;
        this.documentationUrl = documentationUrl;
    }

    /* getters and setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public List<Implementation> getImplementations() {
        return implementations;
    }

    public void setImplementations(List<Implementation> implementations) {
        this.implementations = implementations;
    }

    /* toString method */

    public String toString() {
        return "StandardVersion [id=" + id 
               + ", versionNumber=" + versionNumber
               + ", documentationUrl=" + documentationUrl + "]";
    }
}