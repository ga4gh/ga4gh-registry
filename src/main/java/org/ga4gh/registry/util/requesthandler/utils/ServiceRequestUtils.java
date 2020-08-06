package org.ga4gh.registry.util.requesthandler.utils;

import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.exception.ResourceNotFoundException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.util.hibernate.HibernateUtil;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ServiceRequestUtils {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    @Qualifier(AppConfigConstants.STANDARD_VERSION_HIBERNATE_QUERIER)
    private HibernateQuerier<StandardVersion> svQuerier;

    @Autowired
    private HibernateQueryBuilder svQueryBuilder;

    @Autowired
    @Qualifier(AppConfigConstants.ORGANIZATION_HIBERNATE_QUERIER)
    private HibernateQuerier<Organization> orgQuerier;

    @Autowired
    private HibernateQueryBuilder orgQueryBuilder;

    /* Constructor */

    public ServiceRequestUtils() {
        
    }

    public Implementation preProcessRequestBody(Implementation requestBody) throws ResourceNotFoundException {
        setImplementationCategoryApiService(requestBody);
        setStandardVersionFromType(requestBody);
        setOrganizationIfExists(requestBody);
        return requestBody;
    }

    /**
     * By definition, all Implementations created/updated via /services endpoints
     * belong to the ImplementationCategory of 'APIService'. This method retrieves
     * the appropriate ImplementationCategory from the db, and assigns it to the
     * request Implementation
     * @param requestBody Implementation object passed in request
     */
    private void setImplementationCategoryApiService(Implementation requestBody) {
        String id = Ids.IMPLEMENTATION_CATEGORY_API_SERVICE_ID;
        ImplementationCategory category = (ImplementationCategory) getHibernateUtil().readEntityObject(ImplementationCategory.class, id);
        requestBody.setImplementationCategory(category);
    }

    /**
     * The transient 'type' object passed in the request contains all necessary
     * info for finding the matching Standard and StandardVersion from the db.
     * This method maps 'type' attributes to the correct StandardVersion 
     * (if it exists) and sets it to the request Implementation
     * @param requestBody Implementation object passed in request
     */
    private void setStandardVersionFromType(Implementation requestBody) throws ResourceNotFoundException {
        HibernateQueryBuilder qb = getSvQueryBuilder();
        HibernateQuerier<StandardVersion> querier = getSvQuerier();
        ServiceType type = requestBody.getType();
        if (type == null) {
            throw new BadRequestException("no service type provided");
        }
        qb.setResponseClass(StandardVersion.class);
        qb.join("standard");
        qb.filter("standard.artifact", type.getArtifact());
        qb.filter("versionNumber", type.getVersion());
        querier.setQueryString(qb.build());
        StandardVersion standardVersion = querier.getSingleResult();
        if (standardVersion == null) {
            throw new ResourceNotFoundException("service type " + type.signature() + " not found in registry of GA4GH standards");
        }
        requestBody.setStandardVersion(standardVersion);
    }

    /**
     * The Implementation object's 'organization' can either map to an existing
     * Organization, or represent a new one to be created. This method attempts
     * to find the specified Organization from the db. If it exists, sets it to
     * the request implementation, if not, it will create a new organization.
     * @param requestBody Implementation object passed in request
     */
    private void setOrganizationIfExists(Implementation requestBody) {
        Organization requestBodyOrg = requestBody.getOrganization();
        if (requestBodyOrg == null) {
            throw new BadRequestException("Service does not specify an organization");
        }
        HibernateQueryBuilder qb = getOrgQueryBuilder();
        HibernateQuerier<Organization> querier = getOrgQuerier();
        qb.setResponseClass(Organization.class);
        qb.filter("name", requestBody.getOrganization().getName());
        qb.filter("url", requestBody.getOrganization().getUrl());
        querier.setQueryString(qb.build());
        Organization dbOrganization = querier.getSingleResult();
        if (dbOrganization != null) { // found an existing org, set it to request Body
            requestBody.setOrganization(dbOrganization);
        } else { // org doesn't exist, save transient org first
            getHibernateUtil().createEntityObject(Organization.class, requestBodyOrg);
        }
    }

    /* Setters and Getters */

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public HibernateUtil getHibernateUtil() {
        return hibernateUtil;
    }

    public void setSvQuerier(HibernateQuerier<StandardVersion> svQuerier) {
        this.svQuerier = svQuerier;
    }

    public HibernateQuerier<StandardVersion> getSvQuerier() {
        return svQuerier;
    }

    public void setSvQueryBuilder(HibernateQueryBuilder svQueryBuilder) {
        this.svQueryBuilder = svQueryBuilder;
    }

    public HibernateQueryBuilder getSvQueryBuilder() {
        return svQueryBuilder;
    }

    public void setOrgQuerier(HibernateQuerier<Organization> orgQuerier) {
        this.orgQuerier = orgQuerier;
    }

    public HibernateQuerier<Organization> getOrgQuerier() {
        return orgQuerier;
    }

    public void setOrgQueryBuilder(HibernateQueryBuilder orgQueryBuilder) {
        this.orgQueryBuilder = orgQueryBuilder;
    }

    public HibernateQueryBuilder getOrgQueryBuilder() {
        return orgQueryBuilder;
    }
}