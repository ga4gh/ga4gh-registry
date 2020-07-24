package org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.ga4gh.registry.model.Organization;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.testutils.annotations.RegistryTestProperties;
import org.ga4gh.registry.testutils.deserializer.OrganizationDeserializer;

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

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "com.cge",
                status().isOk(),
                true,
                "Canadian Genomics Engineering",
                "CGE",
                "http://cge.com"
            },
            {
                "org.gdg",
                status().isOk(),
                true,
                "Genomic Developers Group",
                "GDG",
                "http://gdg.org"
            },
            {
                "org.notfound",
                status().isNotFound(),
                false,
                null,
                null,
                null
            }
        };
    }

    @Test
    public void testGetOrganizations() throws Exception {
        MvcResult result = mockMvc.perform(get("/organizations"))
            .andExpect(status().isOk())
            .andReturn();
        
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Organization.class, new OrganizationDeserializer());
        mapper.registerModule(module);
        List<Organization> organizationsList = mapper.readValue(responseBody, new TypeReference<List<Organization>>() {});
        Map<String,Organization> orgMap = organizationsList.stream().collect(Collectors.toMap(Organization::getId, organization -> organization));

        Organization org1 = orgMap.get("org.ga4gh");
        Assert.assertEquals(org1.getName(), "Global Alliance for Genomics and Health");
        Assert.assertEquals(org1.getShortName(), "GA4GH");

        Organization org2 = orgMap.get("com.cge");
        Assert.assertEquals(org2.getName(), "Canadian Genomics Engineering");
        Assert.assertEquals(org2.getShortName(), "CGE");

        Organization org3 = orgMap.get("org.gdg");
        Assert.assertEquals(org3.getName(), "Genomic Developers Group");
        Assert.assertEquals(org3.getShortName(), "GDG");
    }

    @Test(dataProvider = "cases")
    public void testGetOrganizationById(String idString, ResultMatcher expStatus, boolean expSuccess, String expName, String expShortName, String expUrl) throws Exception {
        System.out.println("ID IS: " + idString);

        MvcResult result = mockMvc.perform(get("/organizations/" + idString))
            .andExpect(expStatus)
            .andReturn();

        System.out.println("Response as string is: ");
        System.out.println(result.getResponse().getContentAsString());

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Organization.class, new OrganizationDeserializer());
            mapper.registerModule(module);
            Organization org = mapper.readValue(responseBody, Organization.class);
            
            Assert.assertEquals(org.getId().toString(), idString);
            Assert.assertEquals(org.getName(), expName);
            Assert.assertEquals(org.getShortName(), expShortName);
            Assert.assertEquals(org.getUrl(), expUrl);
        }
    }
}