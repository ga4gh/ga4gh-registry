package org.ga4gh.registry;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;

import org.ga4gh.registry.middleware.AuthorizationInterceptor;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardCategory;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.util.auth.PlaceholderAuth;
import org.ga4gh.registry.util.hibernate.HibernateConfig;
import org.ga4gh.registry.util.hibernate.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.ga4gh.registry.util.requesthandler.delete.DeleteRequestHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexRequestHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexServiceTypesHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexServicesHandler;
import org.ga4gh.registry.util.requesthandler.post.PostRequestHandler;
import org.ga4gh.registry.util.requesthandler.post.PostServiceHandler;
import org.ga4gh.registry.util.requesthandler.put.PutRequestHandler;
import org.ga4gh.registry.util.requesthandler.put.PutServiceHandler;
import org.ga4gh.registry.util.requesthandler.utils.ServiceRequestUtils;
import org.ga4gh.registry.util.requesthandler.show.ShowRequestHandler;
import org.ga4gh.registry.util.requesthandler.show.ShowServiceInfoHandler;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.ga4gh.registry.util.serialize.serializers.ImplementationSerializer;
//import org.ga4gh.registry.util.serialize.serializers.ImplementationSerializer;
import org.ga4gh.registry.util.serialize.serializers.OrganizationSerializer;
import org.ga4gh.registry.util.serialize.serializers.RegistryErrorSerializer;
import org.ga4gh.registry.util.serialize.serializers.ReleaseStatusSerializer;
import org.ga4gh.registry.util.serialize.serializers.ServiceTypeSerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardCategorySerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardSerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardVersionSerializer;
//import org.ga4gh.registry.util.serialize.serializers.ReleaseStatusSerializer;
//import org.ga4gh.registry.util.serialize.serializers.StandardCategorySerializer;
//import org.ga4gh.registry.util.serialize.serializers.StandardSerializer;
import org.ga4gh.registry.util.serialize.serializers.VariableDepthSerializer;
//import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties
public class AppConfig implements WebMvcConfigurer {

    /* ******************************
     * HIGH-LEVEL HIBERNATE-RELATED BEANS
     * ****************************** */

    @Bean
    public HibernateConfig getHibernateConfig() {
        return new HibernateConfig();
    }

    @Bean
    public HibernateUtil getHibernateUtil() {
        return new HibernateUtil();
    }

    @Bean
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQueryBuilder getHibernateQueryBuilder() {
        return new HibernateQueryBuilder();
    }

    /* ******************************
     * HIBERNATE QUERIER BEANS
     * ****************************** */

    @Bean
    @Qualifier(AppConfigConstants.STANDARD_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Standard> getStandardsHibernateQuerier() {
        return new HibernateQuerier<>(Standard.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.STANDARD_VERSION_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<StandardVersion> getStandardVersionsHibernateQuerier() {
        return new HibernateQuerier<>(StandardVersion.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.ORGANIZATION_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Organization> getOrganizationsHibernateQuerier() {
        return new HibernateQuerier<>(Organization.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Implementation> getImplementationsHibernateQuerier() {
        return new HibernateQuerier<>(Implementation.class);
    }

    /* ******************************
     * REQUEST HANDLER BEANS
     * ****************************** */

    /* STANDARD REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexRequestHandler<Standard> standardIndexRequestHandler(
        @Qualifier (AppConfigConstants.BASIC_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier (AppConfigConstants.STANDARD_HIBERNATE_QUERIER) HibernateQuerier<Standard> querier
    ) {
        return new IndexRequestHandler<>(Standard.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Standard> standardShowRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new ShowRequestHandler<>(Standard.class, serializerModule, AppConfigConstants.STANDARD_ID);
    }

    /* STANDARD VERSION REQUEST HANDLER BEANS */

    /* ORGANIZATION REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexRequestHandler<Organization> organizationIndexRequestHandler(
        @Qualifier (AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier (AppConfigConstants.ORGANIZATION_HIBERNATE_QUERIER) HibernateQuerier<Organization> querier
    ) {
        return new IndexRequestHandler<>(Organization.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Organization> organizationShowRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new ShowRequestHandler<>(Organization.class, serializerModule, AppConfigConstants.ORGANIZATION_ID);
    }

    @Bean(name = AppConfigConstants.POST_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostRequestHandler<Organization> organizationPostRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PostRequestHandler<>(Organization.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutRequestHandler<Organization> organizationPutRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PutRequestHandler<>(Organization.class, serializerModule, AppConfigConstants.ORGANIZATION_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Organization> organizationDeleteRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new DeleteRequestHandler<>(Organization.class, serializerModule, AppConfigConstants.ORGANIZATION_ID);
    }

    /* IMPLEMENTATION REQUEST HANDLER BEANS */

    /* SERVICE REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexServicesHandler serviceIndexRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Implementation> querier
    ) {
        return new IndexServicesHandler(Implementation.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Implementation> serviceShowRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new ShowRequestHandler<>(Implementation.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    /*
    @Bean(name = AppConfigConstants.POST_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostServiceHandler servicePostRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new PostServiceHandler(Implementation.class, serializerModuleSet);
    }
    */

    /*
    @Bean(name = AppConfigConstants.PUT_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutServiceHandler servicePutRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new PutServiceHandler(Implementation.class, serializerModuleSet, AppConfigConstants.SERVICE_ID);
    }
    */

    /*
    @Bean(name = AppConfigConstants.DELETE_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Implementation> serviceDeleteRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new DeleteRequestHandler<>(Implementation.class, serializerModuleSet, AppConfigConstants.SERVICE_ID);
    }
    */

    @Bean(name = AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexServiceTypesHandler serviceTypesHandler(
        @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Implementation> querier
    ) {
        return new IndexServiceTypesHandler(Implementation.class, serializerModule, querier);
    }

    /* SERVICE-INFO REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.SHOW_SERVICE_INFO_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowServiceInfoHandler showServiceInfoRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new ShowServiceInfoHandler(Implementation.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    /* ******************************
     * REQUEST HANDLER FACTORY BEANS
     * ****************************** */

    /* STANDARD REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_STANDARD_HANDLER_FACTORY)
    public RequestHandlerFactory<Standard> indexStandardHandlerFactory() {
        return new RequestHandlerFactory<>(Standard.class, AppConfigConstants.INDEX_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_STANDARD_HANDLER_FACTORY)
    public RequestHandlerFactory<Standard> showStandardHandlerFactory() {
        return new RequestHandlerFactory<>(Standard.class, AppConfigConstants.SHOW_STANDARD_HANDLER);
    }

    /* ORGANIZATION REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_ORGANIZATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Organization> indexOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, AppConfigConstants.INDEX_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_ORGANIZATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Organization> showOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, AppConfigConstants.SHOW_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_ORGANIZATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Organization> postOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, AppConfigConstants.POST_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_ORGANIZATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Organization> putOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, AppConfigConstants.PUT_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_ORGANIZATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Organization> deleteOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, AppConfigConstants.DELETE_ORGANIZATION_HANDLER);
    }

    /* SERVICE REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_SERVICE_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> indexServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.INDEX_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_SERVICE_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> showServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.SHOW_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_SERVICE_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> postServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.POST_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_SERVICE_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> putServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.PUT_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_SERVICE_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> deleteServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.DELETE_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> indexServiceTypesHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER);
    }

    /* SERVICE-INFO REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.SHOW_SERVICE_INFO_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> showServiceInfoHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.SHOW_SERVICE_INFO_HANDLER);
    }

    /* ******************************
     * SERIALIZER BEANS
     * ****************************** */

    /* STANDARD SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER)
    public StandardSerializer basicStandardSerializer() {
        return new StandardSerializer();
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER)
    public StandardSerializer relationalStandardSerializer() {
        String[] relationalAttributes = {"description", "versions"};
        return new StandardSerializer(relationalAttributes);
    }

    /* STANDARD CATEGORY SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER)
    public StandardCategorySerializer basicStandardCategorySerializer() {
        return new StandardCategorySerializer();
    }

    /* RELEASE STATUS SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER)
    public ReleaseStatusSerializer basicReleaseStatusSerializer() {
        return new ReleaseStatusSerializer();
    }

    /* STANDARD VERSION SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_VERSION_SERIALIZER)
    public StandardVersionSerializer basicStandardVersionSerializer() {
        return new StandardVersionSerializer();
    }

    /* ORGANIZATION SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER)
    public OrganizationSerializer basicOrganizationSerializer() {
        return new OrganizationSerializer();
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER)
    public OrganizationSerializer relationalOrganizationSerializer() {
        String[] relationalAttributes = {"implementations"};
        return new OrganizationSerializer(relationalAttributes);
    }

    /* IMPLEMENTATION SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER)
    public ImplementationSerializer basicImplementationSerializer() {
        return new ImplementationSerializer();
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER)
    public ImplementationSerializer relationalImplementationSerializer() {
        String[] relationalAttributes = {"organization", "description", "contactUrl", "environment"};
        return new ImplementationSerializer(relationalAttributes);
    }

    /* REGISTRY ERROR SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER)
    public RegistryErrorSerializer basicRegistryErrorSerializer() {
        return new RegistryErrorSerializer();
    }

    /* SERVICE TYPE SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER)
    public ServiceTypeSerializer basicServiceTypeSerializer() {
        return new ServiceTypeSerializer();
    }

    /* ******************************
     * SERIALIZER MODULE BEANS
     * ****************************** */

    /* STANDARD SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER_MODULE)
    public RegistrySerializerModule basicStandardSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER) StandardSerializer standardSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER) StandardCategorySerializer standardCategorySerializer,
        @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER) ReleaseStatusSerializer releaseStatusSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(standardSerializer);
        serializers.add(standardCategorySerializer);
        serializers.add(releaseStatusSerializer);
        return new RegistrySerializerModule("A", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalStandardSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER) StandardSerializer standardSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_VERSION_SERIALIZER) StandardVersionSerializer standardVersionSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER) StandardCategorySerializer standardCategorySerializer,
        @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER) ReleaseStatusSerializer releaseStatusSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(standardSerializer);
        serializers.add(standardVersionSerializer);
        serializers.add(standardCategorySerializer);
        serializers.add(releaseStatusSerializer);
        return new RegistrySerializerModule("B", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    /* ORGANIZATION SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE)
    public RegistrySerializerModule basicOrganizationSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(organizationSerializer);
        return new RegistrySerializerModule("E", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalOrganizationSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer,
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(organizationSerializer);
        serializers.add(implementationSerializer);
        return new RegistrySerializerModule("F", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    /* IMPLEMENTATION SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER_MODULE)
    public RegistrySerializerModule basicImplementationSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(implementationSerializer);
        return new RegistrySerializerModule("G", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalImplementationSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer,
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(implementationSerializer);
        serializers.add(organizationSerializer);
        return new RegistrySerializerModule("H", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    /* REGISTRY ERROR SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER_MODULE)
    public RegistrySerializerModule basicRegistryErrorSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER) RegistryErrorSerializer registryErrorSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(registryErrorSerializer);
        return new RegistrySerializerModule("I", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    /* SERVICE TYPE SERIALIZER MODULE BEANS */
    
    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE)
    public RegistrySerializerModule basicServiceTypeSerializierModule(
        @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER) ServiceTypeSerializer serviceTypeSerializer
    ) {
        List<JsonSerializer<?>> serializers = new ArrayList<>();
        serializers.add(serviceTypeSerializer);
        return new RegistrySerializerModule("J", new Version(1,0,0,"release", "org.ga4gh", "organization"), serializers);
    }

    /* ******************************
     * OTHER UTILS BEANS
     * ****************************** */

    @Bean
    @Scope(AppConfigConstants.PROTOTYPE)
    public ServiceRequestUtils getServiceRequestUtils() {
        return new ServiceRequestUtils();
    }

    /* ******************************
     * MIDDLEWARE BEANS
     * ****************************** */

    @Bean
    public PlaceholderAuth getPlaceholderAuth() {
        return new PlaceholderAuth();
    }

    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor());
    }
}