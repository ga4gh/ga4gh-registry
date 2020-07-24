package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrganizationTest {

    private class TestCase {

        private String id;
        private String name;
        private String shortName;
        private String url;
        private List<Implementation> implementations;
        private String expString;

        public TestCase(String id, String name, String shortName, String url, List<Implementation> implementations, String expString) {
            this.id = id;
            this.name = name;
            this.shortName = shortName;
            this.url = url;
            this.implementations = implementations;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShortName() {
            return shortName;
        }

        public String getUrl() {
            return url;
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
                "d21b145b-0acb-4c5f-83d6-6c2e34884b94",
                "Global Alliance for Genomics and Health",
                "GA4GH",
                "https://ga4gh.org",
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "htsget reference implementation", null, null, null, null, null, null, null, null
                    ));
                }},
                "Organization [id=d21b145b-0acb-4c5f-83d6-6c2e34884b94, name=Global Alliance for Genomics and Health, shortName=GA4GH, url=https://ga4gh.org]"
            )},
            {new TestCase(
                "7f219899-c2ef-4095-9c77-86e69a99f6c6",
                "Genomic Developers Group",
                "GDG",
                "https://gdg.org",
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "refget service", null, null, null, null, null, null, null, null
                    ));
                }},
                "Organization [id=7f219899-c2ef-4095-9c77-86e69a99f6c6, name=Genomic Developers Group, shortName=GDG, url=https://gdg.org]"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testOrganizationNoArgsConstructor(TestCase testCase) throws Exception {

        Organization org = new Organization();
        org.setId(testCase.getId());
        org.setName(testCase.getName());
        org.setShortName(testCase.getShortName());
        org.setUrl(testCase.getUrl());
        org.setImplementations(testCase.getImplementations());
        Assert.assertEquals(org.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(org.getName(), testCase.getName());
        Assert.assertEquals(org.getShortName(), testCase.getShortName());
        Assert.assertEquals(org.getUrl(), testCase.getUrl());
        Assert.assertEquals(org.getImplementations().get(0).getName(), testCase.getImplementations().get(0).getName());
        Assert.assertEquals(org.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testOrganizationAllArgsConstructor(TestCase testCase) throws Exception {

        Organization org = new Organization(testCase.getName(), testCase.getShortName(), testCase.getUrl());
        org.setId(testCase.getId());
        org.setImplementations(testCase.getImplementations());
        Assert.assertEquals(org.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(org.getName(), testCase.getName());
        Assert.assertEquals(org.getShortName(), testCase.getShortName());
        Assert.assertEquals(org.getUrl(), testCase.getUrl());
        Assert.assertEquals(org.getImplementations().get(0).getName(), testCase.getImplementations().get(0).getName());
        Assert.assertEquals(org.toString(), testCase.getExpString());
    }
}