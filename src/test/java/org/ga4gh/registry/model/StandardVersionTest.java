package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StandardVersionTest {

    private class TestCase {

        private String id;
        private Standard standard;
        private String versionNumber;
        private String documentationUrl;
        private ReleaseStatus releaseStatus;
        private List<Implementation> implementations;
        private String expString;

        public TestCase(String id, Standard standard, String versionNumber, String documentationUrl, ReleaseStatus releaseStatus, List<Implementation> implementations, String expString) {
            this.id = id;
            this.standard = standard;
            this.versionNumber = versionNumber;
            this.documentationUrl = documentationUrl;
            this.releaseStatus = releaseStatus;
            this.implementations = implementations;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public Standard getStandard() {
            return standard;
        }

        public String getVersionNumber() {
            return versionNumber;
        }

        public String getDocumentationUrl() {
            return documentationUrl;
        }

        public ReleaseStatus getReleaseStatus() {
            return releaseStatus;
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
                "dca47919-ee0f-4b57-9001-3eb6aa81d863",
                new Standard("htsget", "htsget summary", "htsget description", "https://htsget.org"),
                "1.2.0",
                "https://htsget.org/v/1.2.0",
                new ReleaseStatus("Approved"),
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "htsget reference implementation", null, null, null, null, null, null, null, null
                    ));
                }},
                "StandardVersion [id=dca47919-ee0f-4b57-9001-3eb6aa81d863, versionNumber=1.2.0, documentationUrl=https://htsget.org/v/1.2.0]"
            )},
            {new TestCase(
                "1d639704-93d6-4ed9-a60a-8540c6cef3eb",
                new Standard("wes", "wes summary", "wes description", "https://wes.org"),
                "1.0.0",
                "https://wes.org/v/1.0.0",
                new ReleaseStatus("Approved"),
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "wes reference implementation", null, null, null, null, null, null, null, null
                    ));
                }},
                "StandardVersion [id=1d639704-93d6-4ed9-a60a-8540c6cef3eb, versionNumber=1.0.0, documentationUrl=https://wes.org/v/1.0.0]"
            )},
            {new TestCase(
                "2f60f4bf-d7a9-4eb5-95f5-030e1790e3a4",
                new Standard("refget", "refget summary", "refget description", "https://refget.org"),
                "2.0.0",
                "https://refget.org/v/2.0.0",
                new ReleaseStatus("Approved"),
                new ArrayList<Implementation>() {{
                    add(new Implementation(
                        "refget reference implementation", null, null, null, null, null, null, null, null
                    ));
                }},
                "StandardVersion [id=2f60f4bf-d7a9-4eb5-95f5-030e1790e3a4, versionNumber=2.0.0, documentationUrl=https://refget.org/v/2.0.0]"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testOrganizationNoArgsConstructor(TestCase testCase) throws Exception {

        StandardVersion standardVersion = new StandardVersion();
        standardVersion.setId(testCase.getId());
        standardVersion.setStandard(testCase.getStandard());
        standardVersion.setVersionNumber(testCase.getVersionNumber());
        standardVersion.setDocumentationUrl(testCase.getDocumentationUrl());
        standardVersion.setReleaseStatus(testCase.getReleaseStatus());
        standardVersion.setImplementations(testCase.getImplementations());
        Assert.assertEquals(standardVersion.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(standardVersion.getStandard().getName(), testCase.getStandard().getName());
        Assert.assertEquals(standardVersion.getVersionNumber(), testCase.getVersionNumber());
        Assert.assertEquals(standardVersion.getDocumentationUrl(), testCase.getDocumentationUrl());
        Assert.assertEquals(standardVersion.getReleaseStatus().getStatus(), testCase.getReleaseStatus().getStatus());
        Assert.assertEquals(standardVersion.getImplementations().get(0).getName(), testCase.getImplementations().get(0).getName());
        Assert.assertEquals(standardVersion.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testOrganizationAllArgsConstructor(TestCase testCase) throws Exception {

        StandardVersion standardVersion = new StandardVersion(testCase.getVersionNumber(), testCase.getDocumentationUrl());
        standardVersion.setId(testCase.getId());
        standardVersion.setStandard(testCase.getStandard());
        standardVersion.setReleaseStatus(testCase.getReleaseStatus());
        standardVersion.setImplementations(testCase.getImplementations());
        Assert.assertEquals(standardVersion.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(standardVersion.getStandard().getName(), testCase.getStandard().getName());
        Assert.assertEquals(standardVersion.getVersionNumber(), testCase.getVersionNumber());
        Assert.assertEquals(standardVersion.getDocumentationUrl(), testCase.getDocumentationUrl());
        Assert.assertEquals(standardVersion.getReleaseStatus().getStatus(), testCase.getReleaseStatus().getStatus());
        Assert.assertEquals(standardVersion.getImplementations().get(0).getName(), testCase.getImplementations().get(0).getName());
        Assert.assertEquals(standardVersion.toString(), testCase.getExpString());
    }
}