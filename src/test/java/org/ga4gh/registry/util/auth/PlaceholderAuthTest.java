package org.ga4gh.registry.util.auth;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PlaceholderAuthTest {

    @DataProvider(name = "secrets")
    public Object[][] secrets() {
        return new Object[][] {
            {"secretA"},
            {"secretB"},
            {"secretC"},
        };
    }

    @Test(dataProvider = "secrets")
    public void testSetter(String secret) {
        PlaceholderAuth auth = new PlaceholderAuth();
        auth.setSecret(secret);
        Assert.assertEquals(auth.getSecret(), secret);
    }
}