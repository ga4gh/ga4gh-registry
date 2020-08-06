package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardVersion;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReleaseStatusTest {

    private class TestCase {

        private String id;
        private String status;
        private List<Standard> standards;
        private List<StandardVersion> standardVersions;
        private String expString;

        public TestCase(String id, String status, List<Standard> standards, List<StandardVersion> standardVersions, String expString) {
            this.id = id;
            this.status = status;
            this.standards = standards;
            this.standardVersions = standardVersions;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public List<Standard> getStandards() {
            return standards;
        }

        public List<StandardVersion> getStandardVersions() {
            return standardVersions;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                "03f89c73-c703-4603-8579-fe4c38d94a6a",
                "Approved",
                new ArrayList<Standard>() {{
                    add(new Standard(
                        "htsget",
                        "htsget summary",
                        "htsget description",
                        "https://htsget.org"
                    ));
                }},
                new ArrayList<StandardVersion>() {{
                    add(new StandardVersion(
                        "1.2.0",
                        "https://htsget.org/v1.2.0"
                    ));
                }},
                "ReleaseStatus [id=03f89c73-c703-4603-8579-fe4c38d94a6a, status=Approved]"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testReleaseStatusNoArgsConstructor(TestCase testCase) throws Exception {

        ReleaseStatus releaseStatus = new ReleaseStatus();
        releaseStatus.setId(testCase.getId());
        releaseStatus.setStatus(testCase.getStatus());
        releaseStatus.setStandards(testCase.getStandards());
        releaseStatus.setStandardVersions(testCase.getStandardVersions());
        Assert.assertEquals(releaseStatus.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(releaseStatus.getStatus(), testCase.getStatus());
        Assert.assertEquals(releaseStatus.getStandards().get(0).getName(), testCase.getStandards().get(0).getName());
        Assert.assertEquals(releaseStatus.getStandardVersions().get(0).getVersionNumber(), testCase.getStandardVersions().get(0).getVersionNumber());
        Assert.assertEquals(releaseStatus.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testReleaseStatusAllArgsConstructor(TestCase testCase) throws Exception {

        ReleaseStatus releaseStatus = new ReleaseStatus(testCase.getStatus());
        releaseStatus.setId(testCase.getId());
        releaseStatus.setStandards(testCase.getStandards());
        releaseStatus.setStandardVersions(testCase.getStandardVersions());
        Assert.assertEquals(releaseStatus.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(releaseStatus.getStatus(), testCase.getStatus());
        Assert.assertEquals(releaseStatus.getStandards().get(0).getName(), testCase.getStandards().get(0).getName());
        Assert.assertEquals(releaseStatus.getStandardVersions().get(0).getVersionNumber(), testCase.getStandardVersions().get(0).getVersionNumber());
        Assert.assertEquals(releaseStatus.toString(), testCase.getExpString());
    }
}