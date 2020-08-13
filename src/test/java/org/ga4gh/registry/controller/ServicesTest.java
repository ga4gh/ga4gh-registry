package org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.ga4gh.registry.testutils.ResourceLoader;
import org.ga4gh.registry.testutils.annotations.RegistryTestProperties;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
@RegistryTestProperties
@ContextConfiguration(classes = { AppConfig.class, Services.class })
@WebAppConfiguration
public class ServicesTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    private static final String servicesDir = "/responses/services/";
    private static final String indexDir = servicesDir + "index/";
    private static final String showDir = servicesDir + "show/";
    private static final String typesDir = servicesDir + "types/";

    @DataProvider(name = "indexServicesCases")
    public Object[][] indexServicesCases() {
        return new Object[][] { 
            { false, null, status().isOk(), true, indexDir + "00.json" },
            { true, "org.ga4gh:service-registry:1.0.0", status().isOk(), true, indexDir + "01.json" },
            { true, "org.ga4gh:htsget:1.2.0", status().isOk(), true, indexDir + "02.json" },
            { true, "org.ga4gh:drs:1.0.0", status().isOk(), true, indexDir + "03.json" },
            { true, "BadTypeString", status().isBadRequest(), false, null },
            { true, "BadOrg:htsget:1.2.0", status().isBadRequest(), false, null }
        };
    }

    @DataProvider(name = "showServiceCases")
    public Object[][] showServiceCases() {
        return new Object[][] {
            { "org.ga4gh.registry", status().isOk(), true, showDir + "00.json" },
            { "org.ga4gh.htsgetreference", status().isOk(), true, showDir + "01.json" },
            { "nonexistentid", status().isNotFound(), false, null }
        };
    }

    @Test(dataProvider = "indexServicesCases")
    public void testIndexServices(boolean useTypeString, String typeString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {

        ResultActions actions = null;
        if (useTypeString) {
            actions = mockMvc.perform(get("/services").param("type", typeString));
        } else {
            actions = mockMvc.perform(get("/services"));
        }
        MvcResult result = actions.andExpect(expStatus).andReturn();

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "showServiceCases")
    public void testShowService(String idString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {
        MvcResult result = mockMvc.perform(get("/services/" + idString)).andExpect(expStatus).andReturn();
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test
    public void testServiceTypes() throws Exception {
        MvcResult result = mockMvc.perform(get("/services/types")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expResponseBody = ResourceLoader.load(typesDir + "00.json");
        Assert.assertEquals(responseBody, expResponseBody);
    }
}