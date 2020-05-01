package unit.org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardCategory;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.model.WorkStream;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StandardTest {

    private class TestCase {

        private UUID id;
        private String name;
        private String abbreviation;
        private StandardCategory standardCategory;
        private String summary;
        private String description;
        private String documentationUrl;
        private ReleaseStatus releaseStatus;
        private String artifact;
        private List<StandardVersion> standardVersions;
        private WorkStream workStream;
        private String expString;

        public TestCase(UUID id, String name, String abbreviation, StandardCategory standardCategory, String summary, String description, String documentationUrl, ReleaseStatus releaseStatus, String artifact, List<StandardVersion> standardVersions, WorkStream workStream, String expString) {
            this.id = id;
            this.name = name;
            this.abbreviation = abbreviation;
            this.standardCategory = standardCategory;
            this.summary = summary;
            this.description = description;
            this.documentationUrl = documentationUrl;
            this.releaseStatus = releaseStatus;
            this.artifact = artifact;
            this.standardVersions = standardVersions;
            this.workStream = workStream;
            this.expString = expString;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public StandardCategory getStandardCategory() {
            return standardCategory;
        }

        public String getSummary() {
            return summary;
        }

        public String getDescription() {
            return description;
        }

        public String getDocumentationUrl() {
            return documentationUrl;
        }

        public ReleaseStatus getReleaseStatus() {
            return releaseStatus;
        }

        public String getArtifact() {
            return artifact;
        }

        public List<StandardVersion> getStandardVersions() {
            return standardVersions;
        }

        public WorkStream getWorkStream() {
            return workStream;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                UUID.fromString("86d2f6bd-9969-49d7-bcb7-f51b3887d912"),
                "refget",
                null,
                new StandardCategory("API"),
                "a summary of refget",
                "a long description of refget",
                "https://refget.org",
                new ReleaseStatus("Approved"),
                "refget",
                new ArrayList<StandardVersion>() {{
                    add(new StandardVersion("1.0.0", "https://refget.org/v/1.0.0"));
                }}, 
                new WorkStream("Large Scale Genomics", "LSG"),
                "Standard [id=86d2f6bd-9969-49d7-bcb7-f51b3887d912, name=refget, summary=a summary of refget, description=a long description of refget, documentationUrl=https://refget.org, artifact=refget]"
            )},
            {new TestCase(
                UUID.fromString("67985856-0af8-4ad9-8530-00ae355cc9d3"),
                "Tool Registry Service",
                "trs",
                new StandardCategory("API"),
                "a summary of trs",
                "a long description of trs",
                "https://trs.org",
                new ReleaseStatus("Approved"),
                "trs",
                new ArrayList<StandardVersion>() {{
                    add(new StandardVersion("2.0.0", "https://trs.org/v/2.0.0"));
                }}, 
                new WorkStream("Cloud", null),
                "Standard [id=67985856-0af8-4ad9-8530-00ae355cc9d3, name=Tool Registry Service, summary=a summary of trs, description=a long description of trs, documentationUrl=https://trs.org, artifact=trs]"
            )},
            {new TestCase(
                UUID.fromString("b26e1fff-ac63-4adb-a639-91404ded8ed0"),
                "Pedigree",
                null,
                new StandardCategory("FileFormat"),
                "a summary of pedigree",
                "a long description of pedigree",
                "https://pedigree.org",
                new ReleaseStatus("Proposed"),
                null,
                new ArrayList<StandardVersion>() {{
                    add(new StandardVersion("1.0.0", "https://pedigree.org/v/1.0.0"));
                }}, 
                new WorkStream("Clinical & Phenotypic Data Capture", "Clin/Pheno"),
                "Standard [id=b26e1fff-ac63-4adb-a639-91404ded8ed0, name=Pedigree, summary=a summary of pedigree, description=a long description of pedigree, documentationUrl=https://pedigree.org, artifact=null]"
            )}
        };
    }

    public void assertions(Standard standard, TestCase testCase) {
        Assert.assertEquals(standard.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(standard.getName(), testCase.getName());
        Assert.assertEquals(standard.getAbbreviation(), testCase.getAbbreviation());
        Assert.assertEquals(standard.getStandardCategory().getCategory(), testCase.getStandardCategory().getCategory());
        Assert.assertEquals(standard.getSummary(), testCase.getSummary());
        Assert.assertEquals(standard.getDescription(), testCase.getDescription());
        Assert.assertEquals(standard.getDocumentationUrl(), testCase.getDocumentationUrl());
        Assert.assertEquals(standard.getReleaseStatus().getStatus(), testCase.getReleaseStatus().getStatus());
        Assert.assertEquals(standard.getArtifact(), testCase.getArtifact());
        Assert.assertEquals(standard.getStandardVersions().get(0).getVersionNumber(), testCase.getStandardVersions().get(0).getVersionNumber());
        Assert.assertEquals(standard.getWorkStream().getName(), testCase.getWorkStream().getName());
        Assert.assertEquals(standard.toString(), testCase.getExpString());
    }

    @Test(dataProvider = "cases")
    public void testOrganizationNoArgsConstructor(TestCase testCase) throws Exception {

        Standard standard = new Standard();
        standard.setId(testCase.getId());
        standard.setName(testCase.getName());
        standard.setAbbreviation(testCase.getAbbreviation());
        standard.setStandardCategory(testCase.getStandardCategory());
        standard.setSummary(testCase.getSummary());
        standard.setDescription(testCase.getDescription());
        standard.setDocumentationUrl(testCase.getDocumentationUrl());
        standard.setReleaseStatus(testCase.getReleaseStatus());
        standard.setArtifact(testCase.getArtifact());
        standard.setStandardVersions(testCase.getStandardVersions());
        standard.setWorkStream(testCase.getWorkStream());
        assertions(standard, testCase);
    }

    @Test(dataProvider = "cases")
    public void testOrganizationAllArgsConstructor(TestCase testCase) throws Exception {

        Standard standard = new Standard(testCase.getName(), testCase.getSummary(), testCase.getDescription(), testCase.getDocumentationUrl());
        standard.setId(testCase.getId());
        standard.setAbbreviation(testCase.getAbbreviation());
        standard.setStandardCategory(testCase.getStandardCategory());
        standard.setReleaseStatus(testCase.getReleaseStatus());
        standard.setArtifact(testCase.getArtifact());
        standard.setStandardVersions(testCase.getStandardVersions());
        standard.setWorkStream(testCase.getWorkStream());
        assertions(standard, testCase);
    }
}