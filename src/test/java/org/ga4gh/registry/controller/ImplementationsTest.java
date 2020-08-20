package org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
@ContextConfiguration(classes = { AppConfig.class, Implementations.class })
@WebAppConfiguration
public class ImplementationsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    private static final String implementationsDir = "/responses/implementations/";
    private static final String indexDir = implementationsDir + "index/";
    private static final String showDir = implementationsDir + "show/";
    private static final String postDir = implementationsDir + "post/";
    private static final String putDir = implementationsDir + "put/";

    @DataProvider(name = "indexImplementationsCases")
    public Object[][] indexImplementationsCases() {
        return new Object[][] { 
            { false, null, status().isOk(), true, indexDir + "00.json" },
        };
    }

    @DataProvider(name = "showImplementationCases")
    public Object[][] showImplementationCases() {
        return new Object[][] {
            { "org.broadinstitute.gatk.htsget", status().isOk(), true, showDir + "00.json" }
        };
    }

    @DataProvider(name = "postImplementationCases")
    public Object[][] postImplementationCases() {
        return new Object[][] {
            { postDir + "00.json", HttpHeaderSets.ok(), status().isOk(), true }
        };
    }

    @DataProvider(name = "putImplementationCases")
    public Object[][] putImplementationCases() {
        return new Object[][] {
            { "uk.ac.ebi.refget", putDir + "00.json", HttpHeaderSets.ok(), status().isOk(), true }
        };
    }

    @DataProvider(name = "deleteImplementationCases")
    public Object[][] deleteImplementationCases() {
        return new Object[][] {
            {"uk.ac.ebi.refget", HttpHeaderSets.ok(), status().isOk() }
        };
    }

    @Test(dataProvider = "indexImplementationsCases", groups = "index")
    public void testIndexImplementation(boolean useTypeString, String typeString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {

        ResultActions actions = null;
        if (useTypeString) {
            actions = mockMvc.perform(get("/implementations").param("type", typeString));
        } else {
            actions = mockMvc.perform(get("/implementations"));
        }
        MvcResult result = actions.andExpect(expStatus).andReturn();

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "showImplementationCases", groups = "show", dependsOnGroups = "index")
    public void testShowImplementation(String idString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {
        MvcResult result = mockMvc.perform(get("/implementations/" + idString)).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "postImplementationCases", groups = "post", dependsOnGroups = "show")
    public void testPostImplementation(String payloadFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
        String payload = ResourceLoader.load(payloadFile);
        MockHttpServletRequestBuilder request = post("/implementations");
        request.headers(httpHeaders);
        request.content(payload);
        MvcResult result = mockMvc.perform(request).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            Assert.assertEquals(responseBody, payload);
        }
    }

    @Test(dataProvider = "putImplementationCases", groups = "put", dependsOnGroups = "post")
    public void testPutImplementation(String id, String payloadFile, HttpHeaders httpHeaders, ResultMatcher expStatus, boolean expSuccess) throws Exception {
        String payload = ResourceLoader.load(payloadFile);
        MockHttpServletRequestBuilder request = put("/implementations/" + id);
        request.headers(httpHeaders);
        request.content(payload);
        MvcResult result = mockMvc.perform(request).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            Assert.assertEquals(responseBody, payload);
        }
    }

    @Test(dataProvider = "deleteImplementationCases", groups = "delete", dependsOnGroups = "put")
    public void testDeleteImplementation(String id, HttpHeaders httpHeaders, ResultMatcher expStatus) throws Exception {
        mockMvc.perform(delete("/implementations/" + id).headers(httpHeaders)).andExpect(expStatus).andReturn();
    }
}