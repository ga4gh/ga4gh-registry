package org.ga4gh.implementation.registry.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ga4gh.implementation.registry.util.serializer.ServiceTypeSerializer;

@JsonSerialize(using = ServiceTypeSerializer.class)
public class ServiceType {

    private String group;
    private String artifact;
    private String version;

    public ServiceType() {

    }

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
}