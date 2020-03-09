package org.ga4gh.registry.model;


// @JsonSerialize(using = ServiceTypeSerializer.class)
public class ServiceType {

    private String group;
    private String artifact;
    private String version;

    public ServiceType() {}

    public ServiceType(String group, String artifact, String version) {
        setGroup(group);
        setArtifact(artifact);
        setVersion(version);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString() {
        return "ServiceType ["
               + "group=" + group + ", "
               + "artifact=" + artifact + ", "
               + "version=" + version + "]";
    }

    public boolean equals(Object o) {
        boolean equals = false;
        if (toString().equals(o.toString())) {
            equals = true;
        }
        return equals;
    }

    public int hashCode() {
        return toString().hashCode();
    }
}