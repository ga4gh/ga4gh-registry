package unit.org.ga4gh.registry.controller;

import org.ga4gh.registry.AppConfig;
import org.ga4gh.registry.controller.Standards;
import org.ga4gh.registry.model.Standard;
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
import common.RegistryTestProperties;
import common.deserializer.StandardDeserializer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

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

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "0980a95f-125a-4c4c-bc64-076aa437c083",
                status().isOk(),
                true,
                "refget",
                "Get reference seqs"
            },
            {
                "2ff1eb74-8319-4ac0-b57b-b56705ad156c",
                status().isOk(),
                true,
                "Sequence Alignment Map",
                "Genomic reads file format"
            },
            {
                "3c57ebf6-a020-419f-b3e2-ddbc9ce5f417",
                status().isOk(),
                true,
                "RNAget",
                "Download RNAseq matrix"
            }
        };
    }

    @Test
    public void testGetStandards() throws Exception {
        MvcResult result = mockMvc.perform(get("/standards"))
            .andExpect(status().isOk())
            .andReturn();
        
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Standard.class, new StandardDeserializer());
        mapper.registerModule(module);
        List<Standard> stdList = mapper.readValue(responseBody, new TypeReference<List<Standard>>() {});
        Map<String,Standard> stdMap = stdList.stream().collect(Collectors.toMap(Standard::getName, std -> std));

        Standard std1 = stdMap.get("Phenopackets");
        Assert.assertEquals(std1.getName(), "Phenopackets");
        Assert.assertEquals(std1.getAbbreviation(), null);
        Assert.assertEquals(std1.getSummary(), "Phenotypic data model");
    }

    @Test(dataProvider = "cases")
    public void testGetStandardById(String idString, ResultMatcher expStatus, boolean expSuccess, String expName, String expSummary) throws Exception {

        MvcResult result = mockMvc.perform(get("/standards/" + idString))
            .andExpect(expStatus)
            .andReturn();

        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Standard.class, new StandardDeserializer());
            mapper.registerModule(module);
            Standard std = mapper.readValue(responseBody, Standard.class);
            
            Assert.assertEquals(std.getId().toString(), idString);
            Assert.assertEquals(std.getName(), expName);
            Assert.assertEquals(std.getSummary(), expSummary);
        }
    }
}