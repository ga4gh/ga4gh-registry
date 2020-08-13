package org.ga4gh.registry.constant;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IdsTest {

    @Test
    public void idsTest() throws Exception {
        Ids ids = new Ids();
        ids.getClass();
        Assert.assertEquals(Ids.SERVICE_ID, "org.ga4gh.registry");
        Assert.assertEquals(Ids.GA4GH_ORG_ID, "org.ga4gh");
    }

}