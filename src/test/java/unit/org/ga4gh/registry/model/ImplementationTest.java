package unit.org.ga4gh.registry.model;

import java.util.UUID;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.ImplementationCategory;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardVersion;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ImplementationTest {

    private class TestCase {

        private UUID id;
        private StandardVersion standardVersion;
        private ImplementationCategory implementationCategory;
        private String name;
        private String description;
        private Organization organization;
        private String contactUrl;
        private String documentationUrl;
        private String createdAt;
        private String updatedAt;
        private String environment;
        private String version;
        private String url;
        private String standardArtifact;
        private String expString;

        public TestCase(UUID id, StandardVersion standardVersion, ImplementationCategory implementationCategory, String name, String description, Organization organization, String contactUrl, String documentationUrl, String createdAt, String updatedAt, String environment, String version, String url, String standardArtifact, String expString) {

            this.id = id;
            this.standardVersion = standardVersion;
            this.implementationCategory = implementationCategory;
            this.name = name;
            this.description = description;
            this.organization = organization;
            this.contactUrl = contactUrl;
            this.documentationUrl = documentationUrl;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.environment = environment;
            this.version = version;
            this.url = url;
            this.standardArtifact = standardArtifact;
            this.expString = expString;
        }

        public UUID getId() {
            return id;
        }

        public StandardVersion getStandardVersion() {
            return standardVersion;
        }

        public ImplementationCategory getImplementationCategory() {
            return implementationCategory;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Organization getOrganization() {
            return organization;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public String getDocumentationUrl() {
            return documentationUrl;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getEnvironment() {
            return environment;
        }

        public String getVersion() {
            return version;
        }

        public String getUrl() {
            return url;
        }

        public String getStandardArtifact() {
            return standardArtifact;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                UUID.fromString("a95fcb4c-2754-4a7e-992f-751808bec425"),
                new StandardVersion("1.2.0", "https://htsget.org/v/1.2.0"),
                new ImplementationCategory("APIService"),
                "htsget reference implementation",
                "description of this service",
                new Organization("Global Alliance", "GA4GH", "https://ga4gh.org"),
                "mailto:me@ga4gh.org",
                "https://ga4gh.org/implementations/thisone",
                "2020-05-01T17:48:09+0000",
                "2020-05-01T17:48:09+0000",
                "production",
                "1.0.0",
                "https://htsget.ga4gh.org",
                "htsget",
                "Implementation [id=a95fcb4c-2754-4a7e-992f-751808bec425, name=htsget reference implementation, description=description of this service, contactUrl=mailto:me@ga4gh.org, documentationUrl=https://ga4gh.org/implementations/thisone, createdAt=2020-05-01T17:48:09+0000, updatedAt=2020-05-01T17:48:09+0000, environment=production, version=1.0.0, url=https://htsget.ga4gh.org]"
            )}
        };
    }

    public void assertions(Implementation implementation, TestCase testCase) {
        Assert.assertEquals(implementation.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(implementation.getStandardVersion().getVersionNumber(), testCase.getStandardVersion().getVersionNumber());
        Assert.assertEquals(implementation.getImplementationCategory().getCategory(), testCase.getImplementationCategory().getCategory());
        Assert.assertEquals(implementation.getName(), testCase.getName());
        Assert.assertEquals(implementation.getDescription(), testCase.getDescription());
        Assert.assertEquals(implementation.getOrganization().getName(), testCase.getOrganization().getName());
        Assert.assertEquals(implementation.getContactUrl(), testCase.getContactUrl());
        Assert.assertEquals(implementation.getDocumentationUrl(), testCase.getDocumentationUrl());
        Assert.assertEquals(implementation.getCreatedAt(), testCase.getCreatedAt());
        Assert.assertEquals(implementation.getUpdatedAt(), testCase.getUpdatedAt());
        Assert.assertEquals(implementation.getEnvironment(), testCase.getEnvironment());
        Assert.assertEquals(implementation.getVersion(), testCase.getVersion());
        Assert.assertEquals(implementation.getUrl(), testCase.getUrl());
        Assert.assertEquals(implementation.toString(), testCase.getExpString());
        Assert.assertEquals(implementation.getServiceType().getGroup(), "org.ga4gh");
        Assert.assertEquals(implementation.getServiceType().getArtifact(), testCase.getStandardArtifact());
        Assert.assertEquals(implementation.getServiceType().getVersion(), testCase.getStandardVersion().getVersionNumber());
    }

    @Test(dataProvider = "cases")
    public void testOrganizationNoArgsConstructor(TestCase testCase) throws Exception {

        Implementation implementation = new Implementation();
        implementation.setId(testCase.getId());
        implementation.setStandardVersion(testCase.getStandardVersion());
        implementation.getStandardVersion().setStandard(new Standard());
        implementation.getStandardVersion().getStandard().setArtifact(testCase.getStandardArtifact());
        implementation.setImplementationCategory(testCase.getImplementationCategory());
        implementation.setName(testCase.getName());
        implementation.setDescription(testCase.getDescription());
        implementation.setOrganization(testCase.getOrganization());
        implementation.setContactUrl(testCase.getContactUrl());
        implementation.setDocumentationUrl(testCase.getDocumentationUrl());
        implementation.setCreatedAt(testCase.getCreatedAt());
        implementation.setUpdatedAt(testCase.getUpdatedAt());
        implementation.setEnvironment(testCase.getEnvironment());
        implementation.setVersion(testCase.getVersion());
        implementation.setUrl(testCase.getUrl());
        assertions(implementation, testCase);
    }

    @Test(dataProvider = "cases")
    public void testOrganizationAllArgsConstructor(TestCase testCase) throws Exception {

        Implementation implementation = new Implementation(testCase.getName(), testCase.getDescription(), testCase.getContactUrl(), testCase.getDocumentationUrl(), testCase.getCreatedAt(), testCase.getUpdatedAt(), testCase.getEnvironment(), testCase.getVersion(), testCase.getUrl());
        implementation.setId(testCase.getId());
        implementation.setStandardVersion(testCase.getStandardVersion());
        implementation.getStandardVersion().setStandard(new Standard());
        implementation.getStandardVersion().getStandard().setArtifact(testCase.getStandardArtifact());
        implementation.setImplementationCategory(testCase.getImplementationCategory());
        implementation.setOrganization(testCase.getOrganization());
        assertions(implementation, testCase);
    }
}