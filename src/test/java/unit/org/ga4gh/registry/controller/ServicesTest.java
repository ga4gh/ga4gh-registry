package unit.org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.ga4gh.registry.controller.Services;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ServiceType;
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
import common.RegistryTestProperties;
import common.deserializer.ImplementationDeserializer;
import common.deserializer.ServiceTypeDeserializer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

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

    @DataProvider(name = "getServicesCases")
    public Object[][] dataGetServicesCases() {
        return new Object[][] { { false, null, status().isOk(), true, 2, "GA4GH service registry" },
                { true, "org.ga4gh:service-registry:1.0.0", status().isOk(), true, 1, "GA4GH service registry" },
                { true, "org.ga4gh:htsget:1.2.0", status().isOk(), true, 1, "GA4GH htsget" },
                { true, "org.ga4gh:drs:1.0.0", status().isOk(), true, 0, null },
                { true, "BadTypeString", status().isBadRequest(), false, 0, null },
                { true, "BadOrg:htsget:1.2.0", status().isBadRequest(), false, 0, null } };
    }

    @DataProvider(name = "getServiceByIdCases")
    public Object[][] dataGetServiceByIdCases() {
        return new Object[][] {
                { "7893404d-7b73-4983-9891-89023c8a34fa", status().isOk(), true, "GA4GH service registry" },
                { "a25ff2a4-9233-4192-a029-1a6b69f6aff6", status().isOk(), true, "GA4GH htsget" } };
    }

    @Test(dataProvider = "getServicesCases")
    public void testGetServices(boolean useTypeString, String typeString, ResultMatcher expStatus, boolean expSuccess,
            int expLength, String expImplementationName) throws Exception {

        ResultActions actions = null;
        if (useTypeString) {
            actions = mockMvc.perform(get("/services").param("type", typeString));
        } else {
            actions = mockMvc.perform(get("/services"));
        }
        MvcResult result = actions.andExpect(expStatus).andReturn();

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Implementation.class, new ImplementationDeserializer());
            mapper.registerModule(module);
            List<Implementation> impList = mapper.readValue(responseBody, new TypeReference<List<Implementation>>() {
            });
            Map<String, Implementation> impMap = impList.stream()
                    .collect(Collectors.toMap(Implementation::getName, imp -> imp));

            Assert.assertEquals(impList.size(), expLength);
            if (expLength > 0) {
                Assert.assertEquals(impMap.get(expImplementationName).getName(), expImplementationName);
            }
        }
    }

    @Test(dataProvider = "getServiceByIdCases")
    public void testGetServiceById(String idString, ResultMatcher expStatus, boolean expSuccess, String expName)
            throws Exception {
        MvcResult result = mockMvc.perform(get("/services/" + idString)).andExpect(expStatus).andReturn();

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Implementation.class, new ImplementationDeserializer());
            mapper.registerModule(module);
            Implementation imp = mapper.readValue(responseBody, Implementation.class);

            Assert.assertEquals(imp.getId().toString(), idString);
            Assert.assertEquals(imp.getName(), expName);
        }
    }

    @Test
    public void testGetServiceTypes() throws Exception {
        MvcResult result = mockMvc.perform(get("/services/types")).andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ServiceType.class, new ServiceTypeDeserializer());
        mapper.registerModule(module);
        List<ServiceType> stList = mapper.readValue(responseBody, new TypeReference<List<ServiceType>>() {});
        Map<String, ServiceType> stMap = stList.stream().collect(Collectors.toMap(ServiceType::getArtifact, st -> st));

        Assert.assertEquals(stMap.get("htsget").getGroup(), "org.ga4gh");
        Assert.assertEquals(stMap.get("htsget").getArtifact(), "htsget");
        Assert.assertEquals(stMap.get("htsget").getVersion(), "1.2.0");

        Assert.assertEquals(stMap.get("service-registry").getGroup(), "org.ga4gh");
        Assert.assertEquals(stMap.get("service-registry").getArtifact(), "service-registry");
        Assert.assertEquals(stMap.get("service-registry").getVersion(), "1.0.0");
    }
}