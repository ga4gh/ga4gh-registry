package unit.org.ga4gh.registry.util;

import org.ga4gh.registry.App;
import org.ga4gh.registry.util.HibernateConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

public class HibernateConfigTest {

    private HibernateConfig config;

    @BeforeClass
    public void setup() {
        config = App.getContext().getBean(HibernateConfig.class);
    }

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
        return config.getAllProperties().getProperty(property);
    }
}
