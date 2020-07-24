package org.ga4gh.registry.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistryErrorTest {

    private class TestCase {

        private int status;
        private String message;
        private String expString;

        public TestCase(int status, String message, String expString) {
            this.status = status;
            this.message = message;
            this.expString = expString;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                400,
                "Bad Request",
                "RegistryError [status=400, message=Bad Request]"
            )},
            {new TestCase(
                404,
                "Not Found",
                "RegistryError [status=404, message=Not Found]"
            )},
            {new TestCase(
                500,
                "Server Error",
                "RegistryError [status=500, message=Server Error]"
            )},
        };
    }

    @Test(dataProvider = "cases")
    public void testRegistryErrorNoArgsConstructor(TestCase testCase) throws Exception {

        RegistryError err = new RegistryError();
        err.setStatus(testCase.getStatus());
        err.setMessage(testCase.getMessage());
        Assert.assertEquals(err.getStatus(), testCase.getStatus());
        Assert.assertEquals(err.getMessage(), testCase.getMessage());
        Assert.assertEquals(err.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testRegistryErrorAllArgsConstructor(TestCase testCase) throws Exception {

        RegistryError err = new RegistryError(testCase.getStatus(), testCase.getMessage());
        Assert.assertEquals(err.getStatus(), testCase.getStatus());
        Assert.assertEquals(err.getMessage(), testCase.getMessage());
        Assert.assertEquals(err.toString(), testCase.getExpString());
    }
}