package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class WorkStreamTest {

    private class TestCase {
        
        private String id;
        private String name;
        private String abbreviation;
        private List<Standard> standards;
        private String expString;

        public TestCase(String id, String name, String abbreviation,
            List<Standard> standards, String expString) {
            this.id = id;
            this.name = name;
            this.abbreviation = abbreviation;
            this.standards = standards;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public List<Standard> getStandards() {
            return standards;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                "4113c564-1fbb-40fe-8bad-e6ba5e675e9c",
                "Large Scale Genomics",
                "LSG",
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "refget",
                        "refget summary",
                        "refget description",
                        "https://refget.org"
                    ));
                }},
                "WorkStream [id=4113c564-1fbb-40fe-8bad-e6ba5e675e9c, name=Large Scale Genomics, abbreviation=LSG]"
            )},
            {new TestCase(
                "5125077d-041e-43ce-b9c9-f584153d972e",
                "Cloud",
                null,
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "Workflow Execution Service",
                        "wes summary",
                        "wes description",
                        "https://wes.org"
                    ));
                }},
                "WorkStream [id=5125077d-041e-43ce-b9c9-f584153d972e, name=Cloud, abbreviation=null]"
            )},
            {new TestCase(
                "210c6773-10ad-489a-82d6-72ffa548af0f",
                "Genomic Knowledge Standards",
                "GKS",
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "Variant Representation",
                        "VR summary",
                        "VR description",
                        "https://vr.org"
                    ));
                }},
                "WorkStream [id=210c6773-10ad-489a-82d6-72ffa548af0f, name=Genomic Knowledge Standards, abbreviation=GKS]"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testWorkStreamNoArgsConstructor(TestCase testCase) throws Exception {

        WorkStream workStream = new WorkStream();
        workStream.setId(testCase.getId());
        workStream.setName(testCase.getName());
        workStream.setAbbreviation(testCase.getAbbreviation());
        workStream.setStandards(testCase.getStandards());
        Assert.assertEquals(workStream.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(workStream.getName(), testCase.getName());
        Assert.assertEquals(workStream.getAbbreviation(), testCase.getAbbreviation());
        Assert.assertEquals(workStream.getStandards().get(0).getName(), testCase.getStandards().get(0).getName());
        Assert.assertEquals(workStream.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testWorkStreamAllArgsConstructor(TestCase testCase) throws Exception {

        WorkStream workStream = new WorkStream(testCase.getName(), testCase.getAbbreviation());
        workStream.setId(testCase.getId());
        workStream.setStandards(testCase.getStandards());
        Assert.assertEquals(workStream.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(workStream.getName(), testCase.getName());
        Assert.assertEquals(workStream.getAbbreviation(), testCase.getAbbreviation());
        Assert.assertEquals(workStream.getStandards().get(0).getName(), testCase.getStandards().get(0).getName());
        Assert.assertEquals(workStream.toString(), testCase.getExpString());
    }
}