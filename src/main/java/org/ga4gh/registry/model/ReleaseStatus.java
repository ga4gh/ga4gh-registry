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
@Table(name = "release_status")
public class ReleaseStatus implements RegistryModel {

    @Id
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "releaseStatus",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<Standard> standards;

    @OneToMany(mappedBy = "releaseStatus",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<StandardVersion> standardVersions;

    /* constructors */

    public ReleaseStatus() {

    }

    public ReleaseStatus(String status) {
        this.status = status;
    }

    public void lazyLoad() {
        
    }

    /* getters and setters */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public List<StandardVersion> getStandardVersions() {
        return standardVersions;
    }

    public void setStandardVersions(List<StandardVersion> standardVersions) {
        this.standardVersions = standardVersions;
    }

    /* toString method */

    public String toString() {
        return "ReleaseStatus [id=" + id + ", status=" + status + "]";
    }
}
