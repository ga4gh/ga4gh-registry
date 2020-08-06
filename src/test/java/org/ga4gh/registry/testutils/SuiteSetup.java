package org.ga4gh.registry.testutils;

import org.ga4gh.registry.testutils.annotations.RegistryTestFullContext;
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