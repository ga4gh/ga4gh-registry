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
public class OrganizationsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    private static final String organizationsDir = "/responses/organizations/";
    private static final String indexDir = organizationsDir + "index/";
    private static final String showDir = organizationsDir + "show/";
    private static final String postDir = organizationsDir + "post/";
    private static final String putDir = organizationsDir + "put/";

    @DataProvider(name = "showOrganizationCases")
    public Object[][] showOrganizationCases() {
        return new Object[][] {
            { "org.ga4gh", status().isOk(), true, showDir + "00.json" },
            { "org.broadinstitute", status().isOk(), true, showDir + "01.json" },
            { "uk.ac.ebi", status().isOk(), true, showDir + "02.json" },
            { "org.nonexistent", status().isNotFound(), false, null },
        };
    }

    @DataProvider(name = "postOrganizationCases")
    public Object[][] postOrganizationCases() {
        return new Object[][] {
            {postDir + "00.json", HttpHeaderSets.ok(), status().isOk(), true },
            {postDir + "00.json", HttpHeaderSets.noAuthToken(), status().isForbidden(), false },
            {postDir + "00.json", HttpHeaderSets.authTokenMalformed(), status().isForbidden(), false },
            {postDir + "00.json", HttpHeaderSets.authTokenInvalid(), status().isForbidden(), false }
        };
    }

    @DataProvider(name = "putOrganizationCases")
    public Object[][] putOrganizationCases() {
        return new Object[][] {
            {"org.testa", putDir + "00.json", HttpHeaderSets.ok(), status().isOk(), true }
        };
    }

    @DataProvider(name = "deleteOrganizationCases")
    public Object[][] deleteOrganizationCases() {
        return new Object[][] {
            {"org.testa", HttpHeaderSets.ok(), status().isOk()}
        };
    }

    @Test(groups = "index")
    public void testIndexOrganizations() throws Exception {
        MvcResult result = mockMvc.perform(get("/organizations")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expResponseBody = ResourceLoader.load(indexDir + "00.json");
        Assert.assertEquals(responseBody, expResponseBody);
    }

    @Test(dataProvider = "showOrganizationCases", groups = "show", dependsOnGroups = "index")
    public void testShowOrganization(String idString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {
        MvcResult result = mockMvc.perform(get("/organizations/" + idString)).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "postOrganizationCases", groups = "post", dependsOnGroups = "show")
    public void testPostOrganization(String payloadFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
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

    @Test(dataProvider = "putOrganizationCases", groups = "put", dependsOnGroups = "post")
    public void testPutOrganization(String id, String payloadFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
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

    @Test(dataProvider = "deleteOrganizationCases", groups = "delete", dependsOnGroups = "put")
    public void testDeleteOrganization(String id, HttpHeaders httpHeaders, ResultMatcher expStatus) throws Exception {
        mockMvc.perform(delete("/organizations/" + id).headers(httpHeaders)).andExpect(expStatus).andReturn();
    }
}