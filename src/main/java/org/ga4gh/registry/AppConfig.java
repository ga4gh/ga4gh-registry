package org.ga4gh.registry;

import org.ga4gh.registry.util.HibernateConfig;
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
}