package org.ga4gh.registry;

public class AppConfigConstants {

    /* ******************************
     * URL PATH PARAMETER IDS
     * ****************************** */
    public static final String STANDARD_ID = "standardId";
    public static final String ORGANIZATION_ID = "organizationId";
    public static final String SERVICE_ID = "serviceId";

    /* ******************************
     * HIBERNATE QUERIER
     * ****************************** */

    public static final String STANDARD_HIBERNATE_QUERIER = "standardHibernateQuerier";
    public static final String STANDARD_VERSION_HIBERNATE_QUERIER = "standardVersionHibernateQuerier";
    public static final String ORGANIZATION_HIBERNATE_QUERIER = "organizationHibernateQuerier";
    public static final String IMPLEMENTATION_HIBERNATE_QUERIER = "implementationHibernateQuerier";

    /* ******************************
     * REQUEST HANDLER 
     * ****************************** */

    // STANDARD
    public static final String INDEX_STANDARD_HANDLER = "indexStandardHandler";
    public static final String SHOW_STANDARD_HANDLER = "showStandardHandler";
    // ORGANIZATION
    public static final String INDEX_ORGANIZATION_HANDLER = "indexOrganizationHandler";
    public static final String SHOW_ORGANIZATION_HANDLER = "showOrganizationHandler";
    public static final String POST_ORGANIZATION_HANDLER = "postOrganizationHandler";
    public static final String PUT_ORGANIZATION_HANDLER = "putOrganizationHandler";
    public static final String DELETE_ORGANIZATION_HANDLER = "deleteOrganizationHandler";
    // SERVICE
    public static final String INDEX_SERVICE_HANDLER = "indexServiceHandler";
    public static final String SHOW_SERVICE_HANDLER = "showServiceHandler";
    public static final String POST_SERVICE_HANDLER = "postServiceHandler";
    public static final String PUT_SERVICE_HANDLER = "putServiceHandler";
    public static final String DELETE_SERVICE_HANDLER = "deleteServiceHandler";

    /* ******************************
     * REQUEST HANDLER FACTORY 
     * ****************************** */

    // STANDARD
    public static final String INDEX_STANDARD_HANDLER_FACTORY = "indexStandardHandlerFactory";
    public static final String SHOW_STANDARD_HANDLER_FACTORY = "showStandardHandlerFactory";
    // ORGANIZATION
    public static final String INDEX_ORGANIZATION_HANDLER_FACTORY = "indexOrganizationHandlerFactory";
    public static final String SHOW_ORGANIZATION_HANDLER_FACTORY = "showOrganizationHandlerFactory";
    public static final String POST_ORGANIZATION_HANDLER_FACTORY = "postOrganizationHandlerFactory";
    public static final String PUT_ORGANIZATION_HANDLER_FACTORY = "putOrganizationHandlerFactory";
    public static final String DELETE_ORGANIZATION_HANDLER_FACTORY = "deleteOrganizationHandlerFactory";
    // SERVICE
    public static final String INDEX_SERVICE_HANDLER_FACTORY = "indexServiceHandlerFactory";
    public static final String SHOW_SERVICE_HANDLER_FACTORY = "showServiceHandlerFactory";
    public static final String POST_SERVICE_HANDLER_FACTORY = "postServiceHandlerFactory";
    public static final String PUT_SERVICE_HANDLER_FACTORY = "putServiceHandlerFactory";
    public static final String DELETE_SERVICE_HANDLER_FACTORY = "deleteServiceHandlerFactory";

    /* ******************************
     * SERIALIZER MODULE SETS
     * ****************************** */
    public static final String SHALLOW_STANDARD_SERIALIZER_SET = "shallowStandardSerializerSet";
    public static final String DEEP_STANDARD_SERIALIZER_SET = "deepStandardSerializerSet";
    public static final String SHALLOW_ORGANIZATION_SERIALIZER_SET = "shallowOrganizationSerializerSet";
    public static final String DEEP_ORGANIZATION_SERIALIZER_SET = "deepOrganizationSerializerSet";
    public static final String DEEP_IMPLEMENTATION_SERIALIZER_SET = "deepImplementationSerializerSet";

    /* ******************************
     * MISCELLANEOUS
     * ****************************** */

    public static final String PROTOTYPE = "prototype";
}