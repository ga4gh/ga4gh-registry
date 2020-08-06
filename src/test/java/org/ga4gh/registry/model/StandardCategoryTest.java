package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class StandardCategoryTest {

    private class TestCase {

        private String id;
        private String category;
        private List<Standard> standards;
        private String expString;

        public TestCase(String id, String category, List<Standard> standards, String expString) {
            this.id = id;
            this.category = category;
            this.standards = standards;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public String getCategory() {
            return category;
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
                "e2fded3a-49ae-494d-bc9b-558c0b9e692f",
                "FileFormat",
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "CRAM",
                        "CRAM summary",
                        "CRAM description",
                        "https://cram.org"
                    ));
                }},
                "StandardCategory [id=e2fded3a-49ae-494d-bc9b-558c0b9e692f, category=FileFormat]"
            )},
            {new TestCase(
                "28c662d3-b8d0-47f6-952b-0bfb2037401c",
                "API",
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "htsget",
                        "htsget summary",
                        "htsget description",
                        "https://htsget.org"
                    ));
                }},
                "StandardCategory [id=28c662d3-b8d0-47f6-952b-0bfb2037401c, category=API]"
            )},
            {new TestCase(
                "8adf5368-2566-4123-8b61-9f32c1bd42f3",
                "Schema",
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "Phenopackets",
                        "Phenopackets summary",
                        "Phenopackets description",
                        "https://phenopackets.org"
                    ));
                }},
                "StandardCategory [id=8adf5368-2566-4123-8b61-9f32c1bd42f3, category=Schema]"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testStandardCategoryNoArgsConstructor(TestCase testCase) throws Exception {

        StandardCategory stdCategory = new StandardCategory();
        stdCategory.setId(testCase.getId());
        stdCategory.setCategory(testCase.getCategory());
        stdCategory.setStandards(testCase.getStandards());
        Assert.assertEquals(stdCategory.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(stdCategory.getCategory(), testCase.getCategory());
        Assert.assertEquals(stdCategory.getStandards().get(0).getName(), testCase.getStandards().get(0).getName());
        Assert.assertEquals(stdCategory.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testStandardCategoryAllArgsConstructor(TestCase testCase) throws Exception {

        StandardCategory stdCategory = new StandardCategory(testCase.getCategory());
        stdCategory.setId(testCase.getId());
        stdCategory.setStandards(testCase.getStandards());
        Assert.assertEquals(stdCategory.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(stdCategory.getCategory(), testCase.getCategory());
        Assert.assertEquals(stdCategory.getStandards().get(0).getName(), testCase.getStandards().get(0).getName());
        Assert.assertEquals(stdCategory.toString(), testCase.getExpString());
    }
}