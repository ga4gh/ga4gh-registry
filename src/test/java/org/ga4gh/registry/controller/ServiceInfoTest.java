package org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.ga4gh.registry.model.Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.ga4gh.registry.testutils.annotations.RegistryTestProperties;
import org.ga4gh.registry.testutils.deserializer.ImplementationDeserializer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ga4gh.registry.constant.HttpStatusCode;
import org.ga4gh.registry.constant.Ids;

@Test
@RegistryTestProperties
@ContextConfiguration(classes={AppConfig.class, ServiceInfo.class})
@WebAppConfiguration
public class ServiceInfoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void testGetServiceInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/service-info"))
            .andExpect(status().isOk())
            .andReturn();
        
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Implementation.class, new ImplementationDeserializer());
        mapper.registerModule(module);
        Implementation serviceInfo = mapper.readValue(responseBody, Implementation.class);

        int status = result.getResponse().getStatus();
        Assert.assertEquals(status, Integer.parseInt(HttpStatusCode.OK));
        Assert.assertEquals(serviceInfo.getId().toString(), Ids.SERVICE_ID);
        Assert.assertEquals(serviceInfo.getName(), "GA4GH service registry");
        Assert.assertEquals(serviceInfo.getDescription(), "ga4gh implementation of service registry");
        Assert.assertEquals(serviceInfo.getContactUrl(), "mailto:nobody@ga4gh.org");
        Assert.assertEquals(serviceInfo.getDocumentationUrl(), "https://example.org/doc/ga4gh/registry");
        Assert.assertEquals(serviceInfo.getVersion(), "1.0.0");
        Assert.assertEquals(serviceInfo.getUrl(), "https://example.org/api/ga4gh/registry");
    }
}