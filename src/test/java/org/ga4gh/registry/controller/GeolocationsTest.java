package org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.ga4gh.registry.testutils.HttpHeaderSets;
import org.ga4gh.registry.testutils.ResourceLoader;
import org.ga4gh.registry.testutils.annotations.RegistryTestProperties;

@Test
@RegistryTestProperties
@ContextConfiguration(classes={AppConfig.class, Organizations.class})
@WebAppConfiguration
public class GeolocationsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    private static final String geolocationDir = "/responses/geolocations/";
    private static final String indexDir = geolocationDir + "index/";
    private static final String showDir = geolocationDir + "show/";
    private static final String postDir = geolocationDir + "post/";
    private static final String putDir = geolocationDir + "put/";

    @DataProvider(name = "showGeolocationCases")
    public Object[][] showGeolocationCases() {
        return new Object[][] {
            { "org.ga4gh", status().isOk(), true, showDir + "00.json" },
            { "org.broadinstitute", status().isOk(), true, showDir + "01.json" },
            { "uk.ac.ebi", status().isOk(), true, showDir + "02.json" },
            { "org.nonexistent", status().isNotFound(), false, null },
        };
    }

    @DataProvider(name = "postGeolocationCases")
    public Object[][] postGeolocationCases() {
        return new Object[][] {
            {postDir + "00.json", HttpHeaderSets.ok(), status().isOk(), true },
            {postDir + "00.json", HttpHeaderSets.noAuthToken(), status().isForbidden(), false },
            {postDir + "00.json", HttpHeaderSets.authTokenMalformed(), status().isForbidden(), false },
            {postDir + "00.json", HttpHeaderSets.authTokenInvalid(), status().isForbidden(), false }
        };
    }

    @DataProvider(name = "putGeolocationCases")
    public Object[][] putGeolocationCases() {
        return new Object[][] {
            {"org.testa", putDir + "00.json", HttpHeaderSets.ok(), status().isOk(), true }
        };
    }

    @DataProvider(name = "deleteGeolocationCases")
    public Object[][] deleteGeolocationCases() {
        return new Object[][] {
            {"org.testa", HttpHeaderSets.ok(), status().isOk()}
        };
    }

    @Test(groups = "index")
    public void testIndexGeolocations() throws Exception {
        MvcResult result = mockMvc.perform(get("/organizations")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expResponseBody = ResourceLoader.load(indexDir + "00.json");
        Assert.assertEquals(responseBody, expResponseBody);
    }

    @Test(dataProvider = "showGeolocationCases", groups = "show", dependsOnGroups = "index")
    public void testShowGeolocation(String idString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {
        MvcResult result = mockMvc.perform(get("/organizations/" + idString)).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "postGeolocationCases", groups = "post", dependsOnGroups = "show")
    public void testPostGeolocation(String payloadFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
        String payload = ResourceLoader.load(payloadFile);
        MockHttpServletRequestBuilder request = post("/organizations");
        request.headers(httpHeaders);
        request.content(payload);
        MvcResult result = mockMvc.perform(request).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            Assert.assertEquals(responseBody, payload);
        }
    }

    @Test(dataProvider = "putGeolocationCases", groups = "put", dependsOnGroups = "post")
    public void testPutGeolocation(String id, String payloadFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
        String payload = ResourceLoader.load(payloadFile);
        MockHttpServletRequestBuilder request = put("/organizations/" + id);
        request.headers(httpHeaders);
        request.content(payload);
        MvcResult result = mockMvc.perform(request).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            Assert.assertEquals(responseBody, payload);
        }
    }

    @Test(dataProvider = "deleteGeolocationCases", groups = "delete", dependsOnGroups = "put")
    public void testDeleteGeolocation(String id, HttpHeaders httpHeaders, ResultMatcher expStatus) throws Exception {
        mockMvc.perform(delete("/organizations/" + id).headers(httpHeaders)).andExpect(expStatus).andReturn();
    }
}