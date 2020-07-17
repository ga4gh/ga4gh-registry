package org.ga4gh.registry.util.hibernate;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;

public class HibernateConfig {

    @Value("${hibernate.connection.driver_class}")
    private String connectionDriverClass;

    @Value("${hibernate.connection.url}")
    private String connectionUrl;

    @Value("${hibernate.connection.username}")
    private String connectionUsername;

    @Value("${hibernate.connection.password}")
    private String connectionPassword;

    @Value("${hibernate.connection.pool_size}")
    private String connectionPoolSize;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.current_session_context_class}")
    private String currentSessionContextClass;

    public Properties getAllProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.connection.driver_class", getConnectionDriverClass());
        props.setProperty("hibernate.connection.url", getConnectionUrl());
        props.setProperty("hibernate.connection.username", getConnectionUsername());
        props.setProperty("hibernate.connection.password", getConnectionPassword());
        props.setProperty("hibernate.connection.pool_size", getConnectionPoolSize());
        props.setProperty("hibernate.dialect", getDialect());
        props.setProperty("hibernate.show_sql", getShowSql());
        props.setProperty("hibernate.current_session_context_class", getCurrentSessionContextClass());
        return props;
    }

    public String getConnectionDriverClass() {
        return connectionDriverClass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getConnectionUsername() {
        return connectionUsername;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public String getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public String getDialect() {
        return dialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public String getCurrentSessionContextClass() {
        return currentSessionContextClass;
    }
}