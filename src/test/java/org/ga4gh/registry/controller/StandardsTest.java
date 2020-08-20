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
import org.ga4gh.registry.testutils.HttpHeaderSets;
import org.ga4gh.registry.testutils.ResourceLoader;
import org.ga4gh.registry.testutils.annotations.RegistryTestProperties;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
@RegistryTestProperties
@ContextConfiguration(classes={AppConfig.class, Standards.class})
@WebAppConfiguration
public class StandardsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    private static final String standardsDir = "/responses/standards/";
    private static final String indexDir = standardsDir + "index/";
    private static final String showDir = standardsDir + "show/";
    private static final String postDir = standardsDir + "post/";
    private static final String putDir = standardsDir + "put/";

    @DataProvider(name = "showStandardCases")
    public Object[][] showStandardCases() {
        return new Object[][] {
            { "refget", status().isOk(), true, showDir + "00.json" },
            { "sam", status().isOk(), true, showDir + "01.json" },
            { "rnaget", status().isOk(), true, showDir + "02.json" },
            { "nonexistent", status().isNotFound(), false, null }
        };
    }

    @DataProvider(name = "postStandardCases")
    public Object[][] postStandardCases() {
        return new Object[][] {
            { postDir + "input/00.json", postDir + "result/00.json", HttpHeaderSets.ok(), status().isOk(), true }
        };
    }

    @DataProvider(name = "putStandardCases")
    public Object[][] putStandardCases() {
        return new Object[][] {
            { "standarda", putDir + "input/00.json", putDir + "result/00.json", HttpHeaderSets.ok(), status().isOk(), true }
        };
    }

    @DataProvider(name = "deleteStandardCases")
    public Object[][] deleteStandardCases() {
        return new Object[][] {
            { "standarda", HttpHeaderSets.ok(), status().isOk() }
        };
    }

    @Test(groups = "index")
    public void testIndexStandards() throws Exception {
        MvcResult result = mockMvc.perform(get("/standards")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expResponseBody = ResourceLoader.load(indexDir + "00.json");
        Assert.assertEquals(responseBody, expResponseBody);
    }

    @Test(dataProvider = "showStandardCases", groups = "show", dependsOnGroups = "index")
    public void testShowStandard(String idString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath ) throws Exception {
        MvcResult result = mockMvc.perform(get("/standards/" + idString)).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "postStandardCases", groups = "post", dependsOnGroups = "show")
    public void testPostStandard(String payloadFile, String resultFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
        String payload = ResourceLoader.load(payloadFile);
        MockHttpServletRequestBuilder request = post("/standards");
        request.headers(httpHeaders);
        request.content(payload);
        MvcResult result = mockMvc.perform(request).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResult = ResourceLoader.load(resultFile);
            Assert.assertEquals(responseBody, expResult);
        }
    }

    @Test(dataProvider = "putStandardCases", groups = "put", dependsOnGroups = "post")
    public void testPutStandard(String id, String payloadFile, String resultFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
        String payload = ResourceLoader.load(payloadFile);
        MockHttpServletRequestBuilder request = put("/standards/" + id);
        request.headers(httpHeaders);
        request.content(payload);
        MvcResult result = mockMvc.perform(request).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResult = ResourceLoader.load(resultFile);
            Assert.assertEquals(responseBody, expResult);
        }
    }

    @Test(dataProvider = "deleteStandardCases", groups = "delete", dependsOnGroups = "put")
    public void testDeleteStandard(String id, HttpHeaders httpHeaders, ResultMatcher expStatus) throws Exception {
        mockMvc.perform(delete("/standards/" + id).headers(httpHeaders)).andExpect(expStatus).andReturn();
    }
}