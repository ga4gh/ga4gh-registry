package org.ga4gh.registry;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.util.HibernateConfig;
import org.ga4gh.registry.util.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.RequestHandler;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.ga4gh.registry.util.requesthandler.delete.DeleteRequestHandler;
import org.ga4gh.registry.util.requesthandler.post.PostRequestHandler;
import org.ga4gh.registry.util.requesthandler.post.PostServiceHandler;
import org.ga4gh.registry.util.requesthandler.put.PutRequestHandler;
import org.ga4gh.registry.util.requesthandler.put.PutServiceHandler;
import org.ga4gh.registry.util.requesthandler.requestutils.ServiceRequestUtils;
import org.ga4gh.registry.util.response.HibernateQuerier;
import org.ga4gh.registry.util.response.HibernateQuerierFactory;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.ga4gh.registry.util.response.ResponseEntityCreator;
import org.ga4gh.registry.util.response.ResponseMapper;
import org.ga4gh.registry.util.response.factory.GetOrganizationByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetOrganizationsResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceInfoResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceTypesResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServicesResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetStandardByIdResponseEntityCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetStandardsResponseEntityCreatorFactory;
import org.ga4gh.registry.util.serialize.sets.ImplementationDeepSerializerModuleSet;
import org.ga4gh.registry.util.serialize.sets.OrganizationDeepSerializerModuleSet;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ConfigurationProperties
public class AppConfig {

    /*
     * HIBERNATE CONFIGURATION BEANS
     */

    @Bean
    public HibernateConfig getHibernateConfig() {
        return new HibernateConfig();
    }

    @Bean
    public HibernateUtil getHibernateUtil() {
        return new HibernateUtil();
    }

    /*
     * HIBERNATE QUERIER BEANS
     */

    @Bean
    @Scope("prototype")
    public ResponseMapper getResponseMapper() {
        return new ResponseMapper();
    }

    @Bean
    @Scope("prototype")
    public HibernateQueryBuilder getHibernateQueryBuilder() {
        return new HibernateQueryBuilder();
    }

    @Bean
    public HibernateQuerierFactory getHibernateQuerierFactory() {
        return new HibernateQuerierFactory();
    }

    @Bean(name = "standardsHibernateQuerier")
    @Scope("prototype")
    public HibernateQuerier<Standard> getStandardsHibernateQuerier() {
        return new HibernateQuerier<>(Standard.class);
    }

    @Bean(name = "standardVersionsHibernateQuerier")
    @Scope("prototype")
    public HibernateQuerier<StandardVersion> getStandardVersionsHibernateQuerier() {
        return new HibernateQuerier<>(StandardVersion.class);
    }

    @Bean(name = "implementationsHibernateQuerier")
    @Scope("prototype")
    public HibernateQuerier<Implementation> getImplementationsHibernateQuerier() {
        return new HibernateQuerier<>(Implementation.class);
    }

    @Bean(name = "organizationsHibernateQuerier")
    @Scope("prototype")
    public HibernateQuerier<Organization> getOrganizationsHibernateQuerier() {
        return new HibernateQuerier<>(Organization.class);
    }

    /* ******************************
     * REQUEST HANDLER BEANS
     * ****************************** */

    @Bean(name = "standardsResponseEntityCreator")
    @Scope("prototype")
    public ResponseEntityCreator<Standard> getStandardsResponseEntityCreator() {
        return new ResponseEntityCreator<>(Standard.class);
    }

    /* IMPLEMENTATION REQUEST HANDLER BEANS */

    @Bean(name = "implementationsResponseEntityCreator")
    @Scope("prototype")
    public ResponseEntityCreator<Implementation> getImplementationsResponseEntityCreator() {
        return new ResponseEntityCreator<>(Implementation.class);
    }

    /*
    @Bean(name = "postImplementationHandler")
    @Scope("prototype")
    public PostRequestHandler<Implementation> implementationPostRequestHandler() {
        return new PostRequestHandler<>(Implementation.class);
    }
    */

    /*
    @Bean(name = "putImplementationHandler")
    @Scope("prototype")
    public PutRequestHandler<Implementation> implementationPutRequestHandler() {
        return new PutRequestHandler<>(Implementation.class);
    }

    @Bean(name = "deleteImplementationHandler")
    @Scope("prototype")
    public DeleteRequestHandler<Implementation> implementationDeleteRequestHandler() {
        return new DeleteRequestHandler<>(Implementation.class);
    }
    */

    /* SERVICE REQUEST HANDLER BEANS */

    @Bean(name = "postServiceHandler")
    @Scope("prototype")
    public PostServiceHandler servicePostRequestHandler(
        @Qualifier("implementationDeepSerializerModuleSet") SerializerModuleSet serializerModuleSet
    ) {
        return new PostServiceHandler(Implementation.class, serializerModuleSet);
    }

    @Bean(name = "putServiceHandler")
    @Scope("prototype")
    public PutServiceHandler servicePutRequestHandler(
        @Qualifier("implementationDeepSerializerModuleSet") SerializerModuleSet serializerModuleSet
    ) {
        return new PutServiceHandler(Implementation.class, serializerModuleSet, "serviceId");
    }

    @Bean(name = "deleteServiceHandler")
    @Scope("prototype")
    public DeleteRequestHandler<Implementation> serviceDeleteRequestHandler(
        @Qualifier("implementationDeepSerializerModuleSet") SerializerModuleSet serializerModuleSet
    ) {
        return new DeleteRequestHandler<>(Implementation.class, serializerModuleSet, "serviceId");
    }

    /* ORGANIZATION REQUEST HANDLER BEANS */

    @Bean(name = "organizationsResponseEntityCreator")
    @Scope("prototype")
    public ResponseEntityCreator<Organization> getOrganizationsResponseEntityCreator() {
        return new ResponseEntityCreator<>(Organization.class);
    }

    @Bean(name = "postOrganizationHandler")
    @Scope("prototype")
    public PostRequestHandler<Organization> organizationPostRequestHandler(
        @Qualifier ("organizationDeepSerializerModuleSet") SerializerModuleSet serializerModuleSet
    ) {
        return new PostRequestHandler<>(Organization.class, serializerModuleSet);
    }

    @Bean(name = "putOrganizationHandler")
    @Scope("prototype")
    public PutRequestHandler<Organization> organizationPutRequestHandler(
        @Qualifier ("organizationDeepSerializerModuleSet") SerializerModuleSet serializerModuleSet
    ) {
        return new PutRequestHandler<>(Organization.class, serializerModuleSet, "organizationId");
    }

    @Bean(name = "deleteOrganizationHandler")
    @Scope("prototype")
    public DeleteRequestHandler<Organization> organizationDeleteRequestHandler(
        @Qualifier("organizationDeepSerializerModuleSet") SerializerModuleSet serializerModuleSet
    ) {
        return new DeleteRequestHandler<>(Organization.class, serializerModuleSet, "organizationId");
    }

    /* ******************************
     * REQUEST HANDLER FACTORY BEANS
     * ****************************** */

    @Bean
    public GetStandardsResponseEntityCreatorFactory getStandardsResponseEntityCreatorFactory() {
        return new GetStandardsResponseEntityCreatorFactory(Standard.class, "standards");
    }

    @Bean
    public GetStandardByIdResponseEntityCreatorFactory getStandardByIdResponseEntityCreatorFactory() {
        return new GetStandardByIdResponseEntityCreatorFactory(Standard.class, "standards");
    }

    /* ORGANIZATION REQUEST HANDLER FACTORY BEANS */

    @Bean
    public GetOrganizationsResponseEntityCreatorFactory getOrganizationsResponseEntityCreatorFactory() {
        return new GetOrganizationsResponseEntityCreatorFactory(Organization.class, "organizations");
    }

    @Bean
    public GetOrganizationByIdResponseEntityCreatorFactory getOrganizationByIdResponseEntityCreatorFactory() {
        return new GetOrganizationByIdResponseEntityCreatorFactory(Organization.class, "organizations");
    }

    @Bean
    @Qualifier("postOrganizationHandlerFactory")
    public RequestHandlerFactory<Organization> postOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, "postOrganizationHandler");
    }

    @Bean
    @Qualifier("putOrganizationHandlerFactory")
    public RequestHandlerFactory<Organization> putOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, "putOrganizationHandler");
    }

    @Bean
    @Qualifier("deleteOrganizationHandlerFactory")
    public RequestHandlerFactory<Organization> deleteOrganizationHandlerFactory() {
        return new RequestHandlerFactory<>(Organization.class, "deleteOrganizationHandler");
    }

    /* SERVICE-INFO REQUEST HANDLER FACTORY BEANS */

    @Bean
    public GetServiceInfoResponseEntityCreatorFactory getServiceInfoResponseEntityCreatorFactory() {
        return new GetServiceInfoResponseEntityCreatorFactory(Implementation.class, "implementations");
    }

    /* SERVICE REQUEST HANDLER FACTORY BEANS */

    @Bean
    public GetServicesResponseEntityCreatorFactory getServicesResponseEntityCreatorFactory() {
        return new GetServicesResponseEntityCreatorFactory(Implementation.class, "implementations");
    }

    @Bean
    public GetServiceByIdResponseEntityCreatorFactory getServiceByIdResponseEntityCreatorFactory() {
        return new GetServiceByIdResponseEntityCreatorFactory(Implementation.class, "implementations");
    }

    @Bean
    public GetServiceTypesResponseEntityCreatorFactory getServiceTypesResponseEntityCreatorFactory() {
        return new GetServiceTypesResponseEntityCreatorFactory(Implementation.class, "implementations");
    }

    @Bean
    @Qualifier("postServiceHandlerFactory")
    public RequestHandlerFactory<Implementation> postServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, "postServiceHandler");
    }

    @Bean
    @Qualifier("putServiceHandlerFactory")
    public RequestHandlerFactory<Implementation> putServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, "putServiceHandler");
    }

    @Bean
    @Qualifier("deleteServiceHandlerFactory")
    public RequestHandlerFactory<Implementation> deleteServiceHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, "deleteServiceHandler");
    }
    
    /* ******************************
     * SERIALIZER MODULE SET BEANS
     * ****************************** */

    @Bean
    @Qualifier("organizationDeepSerializerModuleSet")
    public SerializerModuleSet organizationDeepSerializerModuleSet() {
        return new OrganizationDeepSerializerModuleSet();
    }

    @Bean
    @Qualifier("implementationDeepSerializerModuleSet")
    public SerializerModuleSet implementationDeepSerializerModuleSet() {
        return new ImplementationDeepSerializerModuleSet();
    }

    /* ******************************
     * OTHER UTILS BEANS
     * ****************************** */

    @Bean
    @Scope("prototype")
    public ServiceRequestUtils getServiceRequestUtils() {
        return new ServiceRequestUtils();
    }
}