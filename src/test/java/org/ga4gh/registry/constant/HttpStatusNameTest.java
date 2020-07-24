package org.ga4gh.registry.constant;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpStatusNameTest {

    @Test
    public void HttpStatusName() throws Exception {
        HttpStatusName httpStatusName = new HttpStatusName();
        httpStatusName.getClass();
        Assert.assertEquals(HttpStatusName.OK, HttpStatusName.get(HttpStatusCode.OK));
        Assert.assertEquals(HttpStatusName.BAD_REQUEST, HttpStatusName.get(HttpStatusCode.BAD_REQUEST));
        Assert.assertEquals(HttpStatusName.FORBIDDEN, HttpStatusName.get(HttpStatusCode.FORBIDDEN));
    }
}