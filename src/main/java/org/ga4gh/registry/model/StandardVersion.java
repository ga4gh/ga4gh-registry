package org.ga4gh.registry.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "standard_version")
public class StandardVersion implements RegistryModel {

    public static final String tableName = "standard_version";
    
    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_id")
    private Standard standard;

    @Column(name = "version_number")
    private String versionNumber;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "release_status_id")
    private ReleaseStatus releaseStatus;

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

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
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