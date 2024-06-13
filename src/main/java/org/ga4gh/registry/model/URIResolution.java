package org.ga4gh.registry.model;

public class URIResolution implements RegistryModel {

    private static final String tableName = "null";

    private String id;

    private String serviceName;

    /* constructors */

    public URIResolution() {

    }

    public URIResolution(String serviceName) {
        this.serviceName = serviceName;
    }

    public void lazyLoad() {
        
    }

    /* setters and getters */

    public String getTableName() {
        return tableName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
