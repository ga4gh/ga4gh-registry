package org.ga4gh.registry;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.HibernateConfig;
import org.ga4gh.registry.util.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.put.PutOrganizationHandlerFactory;
import org.ga4gh.registry.util.requesthandler.put.PutRequestHandler;
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

    /*
     * RESPONSE ENTITY CREATOR BEANS
     */

    @Bean(name = "standardsResponseEntityCreator")
    @Scope("prototype")
    public ResponseEntityCreator<Standard> getStandardsResponseEntityCreator() {
        return new ResponseEntityCreator<>(Standard.class);
    }

    @Bean(name = "implementationsResponseEntityCreator")
    @Scope("prototype")
    public ResponseEntityCreator<Implementation> getImplementationsResponseEntityCreator() {
        return new ResponseEntityCreator<>(Implementation.class);
    }

    @Bean(name = "organizationsResponseEntityCreator")
    @Scope("prototype")
    public ResponseEntityCreator<Organization> getOrganizationsResponseEntityCreator() {
        return new ResponseEntityCreator<>(Organization.class);
    }

    @Bean(name = "putOrganizationHandler")
    @Scope("prototype")
    public PutRequestHandler<Organization> putRequestHandler() {
        return new PutRequestHandler<>(Organization.class);
    }

    /*
     * RESPONSE ENTITY CREATOR FACTORY BEANS
     */

    @Bean
    public GetStandardsResponseEntityCreatorFactory getStandardsResponseEntityCreatorFactory() {
        return new GetStandardsResponseEntityCreatorFactory(Standard.class, "standards");
    }

    @Bean
    public GetStandardByIdResponseEntityCreatorFactory getStandardByIdResponseEntityCreatorFactory() {
        return new GetStandardByIdResponseEntityCreatorFactory(Standard.class, "standards");
    }

    @Bean
    public GetOrganizationsResponseEntityCreatorFactory getOrganizationsResponseEntityCreatorFactory() {
        return new GetOrganizationsResponseEntityCreatorFactory(Organization.class, "organizations");
    }

    @Bean
    public GetOrganizationByIdResponseEntityCreatorFactory getOrganizationByIdResponseEntityCreatorFactory() {
        return new GetOrganizationByIdResponseEntityCreatorFactory(Organization.class, "organizations");
    }

    @Bean
    public PutOrganizationHandlerFactory PutOrganizationHandlerFactory() {
        return new PutOrganizationHandlerFactory(Organization.class, "putOrganizationHandler", "organizationId");
    }

    @Bean
    public GetServiceInfoResponseEntityCreatorFactory getServiceInfoResponseEntityCreatorFactory() {
        return new GetServiceInfoResponseEntityCreatorFactory(Implementation.class, "implementations");
    }

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
    
    /* Serializer Module Sets */

    @Bean
    @Qualifier("organizationDeepSerializerModuleSet")
    public SerializerModuleSet organizationDeepSerializerModuleSet() {
        return new OrganizationDeepSerializerModuleSet();
    }
}