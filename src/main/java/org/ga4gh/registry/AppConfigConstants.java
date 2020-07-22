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
    public static final String INDEX_SERVICE_TYPES_HANDLER = "indexServiceTypesHandler";
    // SERVICE-INFO
    public static final String SHOW_SERVICE_INFO_HANDLER = "showServiceInfoHandler";

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
    public static final String INDEX_SERVICE_TYPES_HANDLER_FACTORY = "indexServiceTypesHandlerFactory";
    // SERVICE-INFO
    public static final String SHOW_SERVICE_INFO_HANDLER_FACTORY = "showServiceInfoHandlerFactory";

    /* ******************************
     * SERIALIZERS
     * ****************************** */
    // STANDARD
    public static final String BASIC_STANDARD_SERIALIZER = "basicStandardSerializer";
    public static final String RELATIONAL_STANDARD_SERIALIZER = "relationalStandardSerializer";
    // STANDARD CATEGORY
    public static final String BASIC_STANDARD_CATEGORY_SERIALIZER = "basicStandardCategorySerializer";
    // RELEASE STATUS
    public static final String BASIC_RELEASE_STATUS_SERIALIZER = "basicReleaseStatusSerializer";
    // STANDARD VERSION
    public static final String BASIC_STANDARD_VERSION_SERIALIZER = "basicStandardVersionSerializer";
    // ORGANIZATION
    public static final String BASIC_ORGANIZATION_SERIALIZER = "basicOrganizationSerializer";
    public static final String RELATIONAL_ORGANIZATION_SERIALIZER = "relationalOrganizationSerializer";
    // IMPLEMENTATION
    public static final String BASIC_IMPLEMENTATION_SERIALIZER = "basicImplementationSerializer";
    public static final String RELATIONAL_IMPLEMENTATION_SERIALIZER = "relationalImplementationSerializer";
    // WORK STREAM
    public static final String BASIC_WORK_STREAM_SERIALIZER = "basicWorkStreamSerializer";
    // REGISTRY ERROR
    public static final String BASIC_REGISTRY_ERROR_SERIALIZER = "basicRegistryErrorSerializer";
    // SERVICE TYPE
    public static final String BASIC_SERVICE_TYPE_SERIALIZER = "basicServiceTypeSerializer";
    // DATE
    public static final String BASIC_DATE_SERIALIZER = "basicDateSerializer";

    /* ******************************
     * SERIALIZER MODULES
     * ****************************** */
    // STANDARD
    public static final String BASIC_STANDARD_SERIALIZER_MODULE = "basicStandardSerializerModule";
    public static final String RELATIONAL_STANDARD_SERIALIZER_MODULE = "relationalStandardSerializerModule";
    // STANDARD CATEGORY
    public static final String BASIC_STANDARD_CATEGORY_SERIALIZER_MODULE = "basicStandardCategorySerializerModule";
    // RELEASE STATUS
    public static final String BASIC_RELEASE_STATUS_SERIALIZER_MODULE = "basicReleaseStatusSerializerModule";
    // STANDARD VERSION
    public static final String BASIC_STANDARD_VERSION_SERIALIZER_MODULE = "basicStandardVersionSerializerModule";
    // ORGANIZATION
    public static final String BASIC_ORGANIZATION_SERIALIZER_MODULE = "basicOrganizationSerializerModule";
    public static final String RELATIONAL_ORGANIZATION_SERIALIZER_MODULE = "relationalOrganizationSerializerModule";
    // IMPLEMENTATION
    public static final String BASIC_IMPLEMENTATION_SERIALIZER_MODULE = "basicImplementationSerializerModule";
    public static final String RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE = "relationalImplementationSerializerModule";
    // REGISTRY ERROR
    public static final String BASIC_REGISTRY_ERROR_SERIALIZER_MODULE = "basicRegistryErrorSerializerModule";
    // SERVICE TYPE
    public static final String BASIC_SERVICE_TYPE_SERIALIZER_MODULE = "basicServiceTypeSerializerModule";

    /* ******************************
     * MISCELLANEOUS
     * ****************************** */

    public static final String PROTOTYPE = "prototype";
}