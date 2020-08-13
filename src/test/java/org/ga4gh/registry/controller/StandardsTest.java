package org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    @DataProvider(name = "showStandardCases")
    public Object[][] getData() {
        return new Object[][] {
            { "refget", status().isOk(), true, showDir + "00.json" },
            { "sam", status().isOk(), true, showDir + "01.json" },
            { "rnaget", status().isOk(), true, showDir + "02.json" },
            { "nonexistent", status().isNotFound(), false, null }
        };
    }

    @Test
    public void testIndexStandards() throws Exception {
        MvcResult result = mockMvc.perform(get("/standards")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expResponseBody = ResourceLoader.load(indexDir + "00.json");
        Assert.assertEquals(responseBody, expResponseBody);
    }

    @Test(dataProvider = "showStandardCases")
    public void testShowStandard(String idString, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath ) throws Exception {

        MvcResult result = mockMvc.perform(get("/standards/" + idString))
            .andExpect(expStatus)
            .andReturn();

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }
}