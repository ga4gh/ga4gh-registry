package common;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@RegistryTestFullContext
public class SuiteSetup extends AbstractTestNGSpringContextTests {

    @BeforeSuite
    public void setup() {}

    @AfterSuite
    public void close() {}
}