package org.ga4gh.registry.validate;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.util.validate.TypeValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TypeValidatorTest {

    private class TestCase {

        private String type;
        private boolean valid;

        public TestCase(String type, boolean valid) {
            this.type = type;
            this.valid = valid;
        }

        public String getType() {
            return type;
        }

        public boolean getValid() {
            return valid;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase("org.ga4gh:beacon:1.0.0", true)},
            {new TestCase("org.ga4gh:trs:2.0.0", true)},
            {new TestCase("org.ga4gh:beacon", false)},
            {new TestCase("org.ga4gh", false)},
            {new TestCase("org:trs:2.0.0", false)}
        };
    }

    @BeforeClass
    public void setup() {
        new TypeValidator();
    }

    @Test(dataProvider = "cases")
    public void testValidate(TestCase testCase) {

        BadRequestException badRequest = null;
        try {
            TypeValidator.validate(testCase.getType());

        } catch (BadRequestException ex) {
            badRequest = ex;
        }

        if (testCase.getValid()) {
            Assert.assertNull(badRequest);
        } else {
            Assert.assertNotNull(badRequest);
        }
    }
}