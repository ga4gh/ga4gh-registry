package org.ga4gh.registry.util.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.ga4gh.registry.testutils.annotations.RegistryTestFullContext;
import org.testng.Assert;

@RegistryTestFullContext
public class HibernateConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    HibernateConfig hibernateConfig;

    @BeforeClass
    public void setup() {}

    @Test
    public void testProperties() throws Exception {

        Assert.assertEquals(
            getProperty("hibernate.connection.driver_class"),
            "org.postgresql.Driver"
        );

        Assert.assertEquals(
            getProperty("hibernate.connection.url"),
            "jdbc:postgresql://localhost:5432/test_db?currentSchema=test_schema&useSSL=false&serverTimezone=UTC"
        );

        Assert.assertEquals(
            getProperty("hibernate.connection.username"),
            "test_user"
        );

        Assert.assertEquals(
            getProperty("hibernate.connection.password"),
            "password"
        );

        Assert.assertEquals(
            getProperty("hibernate.connection.pool_size"),
            "1"
        );

        Assert.assertEquals(
            getProperty("hibernate.dialect"),
            "org.hibernate.dialect.PostgreSQLDialect"
        );

        Assert.assertEquals(
            getProperty("hibernate.show_sql"),
            "true"
        );

        Assert.assertEquals(
            getProperty("hibernate.current_session_context_class"),
            "thread"
        );
    }

    public String getProperty(String property) {
        return hibernateConfig.getAllProperties().getProperty(property);
    }
}
