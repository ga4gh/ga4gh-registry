package org.ga4gh.registry.model;

import java.util.List;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ga4gh.registry.util.serializer.OrganizationSerializer;
import org.hibernate.annotations.GenericGenerator;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "organization")
@Schema(name = "Organization",
        description = "Organization implementing GA4GH standard(s)")
@JsonSerialize(using = OrganizationSerializer.class)
public class Organization {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
                      strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa9")
    @NotNull
    private UUID id;

    @Column(name = "name")
    @Schema(example = "Global Alliance for Genomics and Health")
    @NotNull
    private String name;

    @Column(name = "short_name")
    @Schema(example = "GA4GH")
    @Null
    private String shortName;

    @Column(name = "url")
    @Schema(example = "https://ga4gh.org")
    @NotNull
    private String url;

    @OneToMany(mappedBy = "organization",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @Schema(required = false)
    private List<Implementation> implementations;

    /* constructors */

    public Organization() {
        
    }

    public Organization(String name, String shortName, String url) {
        this.name = name;
        this.shortName = shortName;
        this.url = url;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Implementation> getImplementations() {
        return implementations;
    }

    public void setImplementations(List<Implementation> implementations) {
        this.implementations = implementations;
    }

    /* toString method */

    public String toString() {
        return "Organization [id=" + id + ", name=" + name +
               ", shortName=" + shortName + ", url=" + url + "]";
    }
}