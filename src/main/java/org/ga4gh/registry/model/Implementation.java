package org.ga4gh.registry.model;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.ga4gh.registry.constant.RegistryConstants;
import org.ga4gh.registry.exception.BadRequestException;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "implementation")
public class Implementation implements RegistryModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
                      strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "standard_version_id")
    private StandardVersion standardVersion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "implementation_category_id")
    private ImplementationCategory implementationCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,
               // cascade = CascadeType.ALL)
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "contact_url")
    private String contactUrl;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "environment")
    private String environment;

    @Column(name = "version")
    private String version;

    @Column(name = "url")
    private String url;

    @Transient
    private ServiceType type;

    public Implementation() {

    }

    public Implementation(String name, String description, String contactUrl,
        String documentationUrl, String createdAt, String updatedAt,
        String environment, String version, String url) {

        this.name = name;
        this.description = description;
        this.contactUrl = contactUrl;
        this.documentationUrl = documentationUrl;
        this.createdAt = parseDate(createdAt);
        this.updatedAt = parseDate(updatedAt);
        this.environment = environment;
        this.version = version;
        this.url = url;
    }

    public Date parseDate(String dateString) {
        try {
            return RegistryConstants.DATE_TIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new BadRequestException("invalid date string format");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StandardVersion getStandardVersion() {
        return standardVersion;
    }

    public void setStandardVersion(StandardVersion standardVersion) {
        this.standardVersion = standardVersion;
    }

    public ImplementationCategory getImplementationCategory() {
        return implementationCategory;
    }

    public void setImplementationCategory(ImplementationCategory implementationCategory) {
        this.implementationCategory = implementationCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public ServiceType getType() {
        return type;
    }

    public ServiceType getServiceType() {
        ServiceType serviceType = new ServiceType();
        if (getStandardVersion() != null) {
            if (getStandardVersion().getStandard() != null) {
                String artifact = getStandardVersion().getStandard().getArtifact();
                String version = getStandardVersion().getVersionNumber();
                serviceType.setGroup("org.ga4gh");
                serviceType.setArtifact(artifact);
                serviceType.setVersion(version);
            }
        }
        return serviceType;
    }

    public String toString() {
        return "Implementation [" +
               "id=" + id + ", "
               + "name=" + name + ", "
               + "description=" + description + ", "
               + "contactUrl=" + contactUrl + ", "
               + "documentationUrl=" + documentationUrl + ", "
               + "createdAt=" + createdAt + ", "
               + "updatedAt=" + updatedAt + ", "
               + "environment=" + environment + ", "
               + "version=" + version + ", "
               + "url=" + url + "]";
    }
}