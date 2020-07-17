package org.ga4gh.registry;

import java.util.ArrayList;
import java.util.Arrays;

import org.ga4gh.registry.middleware.AuthorizationInterceptor;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
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
import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.RegistryErrorSerializerModule;
import org.ga4gh.registry.util.serialize.modules.ReleaseStatusSerializerModule;
import org.ga4gh.registry.util.serialize.modules.ServiceTypeSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardCategorySerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardSerializerModule;
import org.ga4gh.registry.util.serialize.modules.StandardVersionSerializerModule;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
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
        @Qualifier (AppConfigConstants.SHALLOW_STANDARD_SERIALIZER_SET) SerializerModuleSet serializerModuleSet,
        @Qualifier (AppConfigConstants.STANDARD_HIBERNATE_QUERIER) HibernateQuerier<Standard> querier
    ) {
        return new IndexRequestHandler<>(Standard.class, serializerModuleSet, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Standard> standardShowRequestHandler(
        @Qualifier (AppConfigConstants.DEEP_STANDARD_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new ShowRequestHandler<>(Standard.class, serializerModuleSet, AppConfigConstants.STANDARD_ID);
    }

    /* STANDARD VERSION REQUEST HANDLER BEANS */

    /* ORGANIZATION REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexRequestHandler<Organization> organizationIndexRequestHandler(
        @Qualifier (AppConfigConstants.SHALLOW_ORGANIZATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet,
        @Qualifier (AppConfigConstants.ORGANIZATION_HIBERNATE_QUERIER) HibernateQuerier<Organization> querier
    ) {
        return new IndexRequestHandler<>(Organization.class, serializerModuleSet, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Organization> organizationShowRequestHandler(
        @Qualifier (AppConfigConstants.SHALLOW_ORGANIZATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new ShowRequestHandler<>(Organization.class, serializerModuleSet, AppConfigConstants.ORGANIZATION_ID);
    }

    @Bean(name = AppConfigConstants.POST_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostRequestHandler<Organization> organizationPostRequestHandler(
        @Qualifier (AppConfigConstants.DEEP_ORGANIZATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new PostRequestHandler<>(Organization.class, serializerModuleSet);
    }

    @Bean(name = AppConfigConstants.PUT_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutRequestHandler<Organization> organizationPutRequestHandler(
        @Qualifier (AppConfigConstants.DEEP_ORGANIZATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new PutRequestHandler<>(Organization.class, serializerModuleSet, AppConfigConstants.ORGANIZATION_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Organization> organizationDeleteRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_ORGANIZATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new DeleteRequestHandler<>(Organization.class, serializerModuleSet, AppConfigConstants.ORGANIZATION_ID);
    }

    /* IMPLEMENTATION REQUEST HANDLER BEANS */

    /* SERVICE REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexServicesHandler serviceIndexRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet,
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Implementation> querier
    ) {
        return new IndexServicesHandler(Implementation.class, serializerModuleSet, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowRequestHandler<Implementation> serviceShowRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new ShowRequestHandler<>(Implementation.class, serializerModuleSet, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.POST_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostServiceHandler servicePostRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new PostServiceHandler(Implementation.class, serializerModuleSet);
    }

    @Bean(name = AppConfigConstants.PUT_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutServiceHandler servicePutRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new PutServiceHandler(Implementation.class, serializerModuleSet, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public DeleteRequestHandler<Implementation> serviceDeleteRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new DeleteRequestHandler<>(Implementation.class, serializerModuleSet, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexServiceTypesHandler serviceTypesHandler(
        @Qualifier(AppConfigConstants.SERVICE_TYPE_SERIALIZER_SET) SerializerModuleSet serializerModuleSet,
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Implementation> querier
    ) {
        return new IndexServiceTypesHandler(Implementation.class, serializerModuleSet, querier);
    }

    /* SERVICE-INFO REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.SHOW_SERVICE_INFO_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowServiceInfoHandler showServiceInfoRequestHandler(
        @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET) SerializerModuleSet serializerModuleSet
    ) {
        return new ShowServiceInfoHandler(Implementation.class, serializerModuleSet, AppConfigConstants.SERVICE_ID);
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
     * SERIALIZER MODULE SET BEANS
     * ****************************** */

    @Bean
    @Qualifier(AppConfigConstants.SHALLOW_STANDARD_SERIALIZER_SET)
    public SerializerModuleSet shallowStandardSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new StandardSerializerModule());
        set.addModule(new StandardCategorySerializerModule());
        set.addModule(new ReleaseStatusSerializerModule());
        set.registerModules();
        return set;
    }

    @Bean
    @Qualifier(AppConfigConstants.DEEP_STANDARD_SERIALIZER_SET)
    public SerializerModuleSet deepStandardSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new StandardSerializerModule());
        set.addModule(new StandardCategorySerializerModule());
        set.addModule(new ReleaseStatusSerializerModule());
        set.addModule(new StandardVersionSerializerModule());
        set.registerModules();
        return set;
    }

    @Bean
    @Qualifier(AppConfigConstants.SHALLOW_ORGANIZATION_SERIALIZER_SET)
    public SerializerModuleSet shallowOrganizationSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new OrganizationSerializerModule());
        set.registerModules();
        return set;
    }

    @Bean
    @Qualifier(AppConfigConstants.DEEP_ORGANIZATION_SERIALIZER_SET)
    public SerializerModuleSet deepOrganizationSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new OrganizationSerializerModule());
        set.addModule(new ImplementationSerializerModule());
        set.registerModules();
        return set;
    }

    @Bean
    @Qualifier(AppConfigConstants.DEEP_IMPLEMENTATION_SERIALIZER_SET)
    public SerializerModuleSet deepImplementationSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new ImplementationSerializerModule());
        set.addModule(new OrganizationSerializerModule());
        set.registerModules();
        return set;
    }

    @Bean
    @Qualifier(AppConfigConstants.SERVICE_TYPE_SERIALIZER_SET)
    public SerializerModuleSet serviceTypeSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new ServiceTypeSerializerModule());
        set.registerModules();
        return set;
    }

    @Bean
    @Qualifier(AppConfigConstants.REGISTRY_ERROR_SERIALIZER_SET)
    public SerializerModuleSet registryErrorSerializerSet() {
        SerializerModuleSet set = new SerializerModuleSet();
        set.addModule(new RegistryErrorSerializerModule());
        set.registerModules();
        return set;
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