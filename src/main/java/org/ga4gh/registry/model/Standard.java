package org.ga4gh.registry.model;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="standard")
public class Standard implements RegistryModel {

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_category_id")
    private StandardCategory standardCategory;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "release_status_id")
    private ReleaseStatus releaseStatus;

    @Column(name = "artifact")
    private String artifact;

    @OneToMany(mappedBy = "standard",
               fetch = FetchType.LAZY,
               cascade = {})//CascadeType.ALL)
    private List<StandardVersion> standardVersions;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "work_stream_id")
    private WorkStream workStream;

    /* constructors */

    public Standard() {

    }

    public Standard(String name, String summary, String description, 
        String documentationUrl) {
            this.name = name;
            this.summary = summary;
            this.description = description;
            this.documentationUrl = documentationUrl;
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

    public StandardCategory getStandardCategory() {
        return standardCategory;
    }

    public void setStandardCategory(StandardCategory standardCategory) {
        this.standardCategory = standardCategory;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public List<StandardVersion> getStandardVersions() {
        return standardVersions;
    }

    public void setStandardVersions(List<StandardVersion> standardVersions) {
        this.standardVersions = standardVersions;
    }

    public WorkStream getWorkStream() {
        return workStream;
    }

    public void setWorkStream(WorkStream workStream) {
        this.workStream = workStream;
    }

    public String toString() {
        return "Standard [id=" + id + ", name=" + name + 
               ", summary=" + summary +
               ", description=" + description +
               ", documentationUrl=" + documentationUrl +
               ", artifact=" + artifact + "]";
    }
}
