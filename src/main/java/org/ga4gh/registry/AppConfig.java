package org.ga4gh.registry;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.HibernateConfig;
import org.ga4gh.registry.util.response.factory.GetOrganizationByIdResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetOrganizationsResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceByIdResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceInfoResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServiceTypesResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetServicesResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetStandardByIdResponseCreatorFactory;
import org.ga4gh.registry.util.response.factory.GetStandardsResponseCreatorFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class AppConfig {

    @Bean
    public HibernateConfig getHibernateConfig() {
        return new HibernateConfig();
    }

    @Bean
    public GetOrganizationsResponseCreatorFactory getOrganizationsResponseCreatorFactory() {
        return new GetOrganizationsResponseCreatorFactory(Organization.class);
    }

    @Bean
    public GetOrganizationByIdResponseCreatorFactory getOrganizationByIdResponseCreatorFactory() {
        return new GetOrganizationByIdResponseCreatorFactory(Organization.class);
    }

    @Bean
    public GetServiceInfoResponseCreatorFactory getServiceInfoResponseCreatorFactory() {
        return new GetServiceInfoResponseCreatorFactory(Implementation.class);
    }

    @Bean
    public GetServicesResponseCreatorFactory getServicesResponseCreatorFactory() {
        return new GetServicesResponseCreatorFactory(Implementation.class);
    }

    @Bean
    public GetServiceByIdResponseCreatorFactory getServiceByIdResponseCreatorFactory() {
        return new GetServiceByIdResponseCreatorFactory(Implementation.class);
    }

    @Bean
    public GetServiceTypesResponseCreatorFactory getServiceTypesResponseCreatorFactory() {
        return new GetServiceTypesResponseCreatorFactory(Implementation.class);
    }

    @Bean
    public GetStandardsResponseCreatorFactory getStandardsResponseCreatorFactory() {
        return new GetStandardsResponseCreatorFactory(Standard.class);
    }

    @Bean
    public GetStandardByIdResponseCreatorFactory getStandardByIdResponseCreatorFactory() {
        return new GetStandardByIdResponseCreatorFactory(Standard.class);
    }
}