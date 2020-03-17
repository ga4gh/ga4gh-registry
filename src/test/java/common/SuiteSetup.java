package common;

import org.ga4gh.registry.App;
import org.ga4gh.registry.util.HibernateUtil;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SuiteSetup {

    public static String[] args = {"--spring.config.location=file:./config/test.application.properties"};

    @BeforeSuite
    public void setup() {
        App.start(args);
    }

    @AfterSuite
    public void close() {
        HibernateUtil.shutdown();
    }
}