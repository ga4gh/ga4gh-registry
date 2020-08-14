package org.ga4gh.registry;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.ga4gh.registry.middleware.AuthorizationInterceptor;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.util.auth.PlaceholderAuth;
import org.ga4gh.registry.util.hibernate.HibernateConfig;
import org.ga4gh.registry.util.hibernate.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.ga4gh.registry.util.requesthandler.delete.DeleteImplementationHandler;
import org.ga4gh.registry.util.requesthandler.delete.DeleteRequestHandler;
import org.ga4gh.registry.util.requesthandler.delete.DeleteServiceHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexImplementationsHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexRequestHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexServiceTypesHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexServicesHandler;
import org.ga4gh.registry.util.requesthandler.post.PostImplementationHandler;
import org.ga4gh.registry.util.requesthandler.post.PostRequestHandler;
import org.ga4gh.registry.util.requesthandler.post.PostServiceHandler;
import org.ga4gh.registry.util.requesthandler.put.PutImplementationHandler;
import org.ga4gh.registry.util.requesthandler.put.PutRequestHandler;
import org.ga4gh.registry.util.requesthandler.put.PutServiceHandler;
import org.ga4gh.registry.util.requesthandler.utils.ImplementationRequestUtils;
import org.ga4gh.registry.util.requesthandler.show.ShowImplementationHandler;
import org.ga4gh.registry.util.requesthandler.show.ShowRequestHandler;
import org.ga4gh.registry.util.requesthandler.show.ShowServiceHandler;
import org.ga4gh.registry.util.requesthandler.show.ShowServiceInfoHandler;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.ga4gh.registry.util.serialize.RegistrySerializerModuleHelper;
import org.ga4gh.registry.util.serialize.serializers.DateSerializer;
import org.ga4gh.registry.util.serialize.serializers.ImplementationSerializer;
import org.ga4gh.registry.util.serialize.serializers.OrganizationSerializer;
import org.ga4gh.registry.util.serialize.serializers.RegistryErrorSerializer;
import org.ga4gh.registry.util.serialize.serializers.ReleaseStatusSerializer;
import org.ga4gh.registry.util.serialize.serializers.ServiceTypeSerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardCategorySerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardSerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardVersionSerializer;
import org.ga4gh.registry.util.serialize.serializers.WorkStreamSerializer;
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
     * LOW-LEVEL HIBERNATE-RELATED BEANS
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

    @Bean(name = AppConfigConstants.POST_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostRequestHandler<Standard> standardPostRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PostRequestHandler<>(Standard.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutRequestHandler<Standard> standardPutRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PutRequestHandler<>(Standard.class, serializerModule, AppConfigConstants.STANDARD_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Standard> standardDeleteRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new DeleteRequestHandler<>(Standard.class, serializerModule, AppConfigConstants.STANDARD_ID);
    }

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

    @Bean(name = AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexImplementationsHandler implementationIndexRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Implementation> querier
    ) {
        return new IndexImplementationsHandler(Implementation.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Implementation> implementationShowRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new ShowImplementationHandler(Implementation.class, serializerModule, AppConfigConstants.IMPLEMENTATION_ID);
    }

    @Bean(name = AppConfigConstants.POST_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostRequestHandler<Implementation> implementationPostRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PostImplementationHandler(Implementation.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutRequestHandler<Implementation> implementationPutRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PutImplementationHandler(Implementation.class, serializerModule, AppConfigConstants.IMPLEMENTATION_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Implementation> implementationDeleteRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new DeleteImplementationHandler(Implementation.class, serializerModule, AppConfigConstants.IMPLEMENTATION_ID);
    }

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
        return new ShowServiceHandler(Implementation.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.POST_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostServiceHandler servicePostRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PostServiceHandler(Implementation.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutServiceHandler servicePutRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PutServiceHandler(Implementation.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Implementation> serviceDeleteRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new DeleteServiceHandler(Implementation.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

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

    @Bean
    @Qualifier(AppConfigConstants.POST_STANDARD_HANDLER_FACTORY)
    public RequestHandlerFactory<Standard> postStandardHandlerFactory() {
        return new RequestHandlerFactory<>(Standard.class, AppConfigConstants.POST_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_STANDARD_HANDLER_FACTORY)
    public RequestHandlerFactory<Standard> putStandardHandlerFactory() {
        return new RequestHandlerFactory<>(Standard.class, AppConfigConstants.PUT_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_STANDARD_HANDLER_FACTORY)
    public RequestHandlerFactory<Standard> deleteStandardHandlerFactory() {
        return new RequestHandlerFactory<>(Standard.class, AppConfigConstants.DELETE_STANDARD_HANDLER);
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

    /* IMPLEMENTATION REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> indexImplementationHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> showImplementationHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_IMPLEMENTATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> postImplementationHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.POST_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_IMPLEMENTATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> putImplementationHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.PUT_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation> deleteImplementationHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER);
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
        String[] relationalAttributes = {"description", "versions", "workStream"};
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

    /* WORK STREAM SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_WORK_STREAM_SERIALIZER)
    public WorkStreamSerializer basicWorkStreamSerializer() {
        return new WorkStreamSerializer();
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

    /* DATE SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER)
    public DateSerializer basicDateSerializer() {
        return new DateSerializer();
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
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_STANDARD_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("standard"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {standardSerializer, standardCategorySerializer, releaseStatusSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalStandardSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER) StandardSerializer standardSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_VERSION_SERIALIZER) StandardVersionSerializer standardVersionSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER) StandardCategorySerializer standardCategorySerializer,
        @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER) ReleaseStatusSerializer releaseStatusSerializer,
        @Qualifier(AppConfigConstants.BASIC_WORK_STREAM_SERIALIZER) WorkStreamSerializer workStreamSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("standard"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {standardSerializer, standardVersionSerializer, standardCategorySerializer, releaseStatusSerializer, workStreamSerializer})
        );
    }

    /* ORGANIZATION SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE)
    public RegistrySerializerModule basicOrganizationSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("organization"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {organizationSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalOrganizationSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer,
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("organization"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {organizationSerializer, implementationSerializer})
        );
    }

    /* IMPLEMENTATION SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER_MODULE)
    public RegistrySerializerModule basicImplementationSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("implementation"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {implementationSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalImplementationSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer,
        @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER) DateSerializer dateSerializer,
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("implementation"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {implementationSerializer, dateSerializer, organizationSerializer})
        );
    }

    /* REGISTRY ERROR SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER_MODULE)
    public RegistrySerializerModule basicRegistryErrorSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER) RegistryErrorSerializer registryErrorSerializer,
        @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER) DateSerializer dateSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("registryError"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {registryErrorSerializer, dateSerializer})
        );
    }

    /* SERVICE TYPE SERIALIZER MODULE BEANS */
    
    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE)
    public RegistrySerializerModule basicServiceTypeSerializierModule(
        @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER) ServiceTypeSerializer serviceTypeSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("serviceType"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {serviceTypeSerializer})
        );
    }

    /* ******************************
     * OTHER UTILS BEANS
     * ****************************** */

    @Bean
    @Scope(AppConfigConstants.PROTOTYPE)
    public ImplementationRequestUtils getServiceRequestUtils() {
        return new ImplementationRequestUtils();
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