package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ImplementationCategoryTest {

    private class TestCase {

        private String id;
        private String category;
        private List<Implementation> implementations;
        private String expString;

        public TestCase(String id, String category, List<Implementation> implementations, String expString) {
            this.id = id;
            this.category = category;
            this.implementations = implementations;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public String getCategory() {
            return category;
        }

        public List<Implementation> getImplementations() {
            return implementations;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                "71a56d65-e674-4896-aaa3-9bb0ef05c260",
                "APIService",
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "htsget reference implementation", null, null, null, null, null, null, null, null     
                    ));
                }},
                "ImplementationCategory [id=71a56d65-e674-4896-aaa3-9bb0ef05c260, category=APIService]"
            )},
            {new TestCase(
                "97066ea3-59d2-4127-aec5-6bf94ecbf609",
                "FileFormatTool",
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "Genome Analysis Toolkit", null, null, null, null, null, null, null, null     
                    ));
                }},
                "ImplementationCategory [id=97066ea3-59d2-4127-aec5-6bf94ecbf609, category=FileFormatTool]"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testImplementationCategoryNoArgsConstructor(TestCase testCase) throws Exception {

        ImplementationCategory cat = new ImplementationCategory();
        cat.setId(testCase.getId());
        cat.setCategory(testCase.getCategory());
        cat.setImplementations(testCase.getImplementations());
        Assert.assertEquals(cat.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(cat.getCategory(), testCase.getCategory());
        Assert.assertEquals(cat.getImplementations().get(0).getName(), testCase.getImplementations().get(0).getName());
        Assert.assertEquals(cat.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testImplementationCategoryAllArgsConstructor(TestCase testCase) throws Exception {

        ImplementationCategory cat = new ImplementationCategory(testCase.getCategory());
        cat.setId(testCase.getId());
        cat.setImplementations(testCase.getImplementations());
        Assert.assertEquals(cat.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(cat.getCategory(), testCase.getCategory());
        Assert.assertEquals(cat.getImplementations().get(0).getName(), testCase.getImplementations().get(0).getName());
        Assert.assertEquals(cat.toString(), testCase.getExpString());
    }
}