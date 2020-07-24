package org.ga4gh.registry.model;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.model.ServiceType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ServiceTypeTest {
    
    private class TestCase {

        private String group;
        private String artifact;
        private String version;
        private String expString;
        private int expHashcode;

        public TestCase(String group, String artifact, String version, String expString, int expHashcode) {
            this.group = group;
            this.artifact = artifact;
            this.version = version;
            this.expString = expString;
            this.expHashcode = expHashcode;
        }

        public String getGroup() {
            return group;
        }

        public String getArtifact() {
            return artifact;
        }

        public String getVersion() {
            return version;
        }

        public String getExpString() {
            return expString;
        }

        public int getExpHashcode() {
            return expHashcode;
        }
    }

    private class TypeStringTestCase {

        private String typeString;
        private boolean expSuccess;
        private String expExceptionClass;
        private String expExceptionMessage;

        public TypeStringTestCase(String typeString, boolean expSuccess, String expExceptionClass, String expExceptionMessage) {
            this.typeString = typeString;
            this.expSuccess = expSuccess;
            this.expExceptionClass = expExceptionClass;
            this.expExceptionMessage = expExceptionMessage;
        }

        public String getTypeString() {
            return typeString;
        }

        public boolean getExpSuccess() {
            return expSuccess;
        }

        public String getExpExceptionClass() {
            return expExceptionClass;
        }

        public String getExpExceptionMessage() {
            return expExceptionMessage;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                "org.ga4gh",
                "htsget",
                "1.2.0",
                "ServiceType [group=org.ga4gh, artifact=htsget, version=1.2.0]",
                909141084
            )},
            {new TestCase(
                "org.ga4gh",
                "refget",
                "2.0.0",
                "ServiceType [group=org.ga4gh, artifact=refget, version=2.0.0]",
                -118531823
            )},
            {new TestCase(
                "org.ga4gh",
                "drs",
                "1.0.0",
                "ServiceType [group=org.ga4gh, artifact=drs, version=1.0.0]",
                527341720
            )}
        };
    }

    @DataProvider(name = "typeStringCases")
    public Object[][] getTypeStringCases() {
        return new Object[][] {
            {new TypeStringTestCase(
                "org.ga4gh:refget:1.0.0",
                true,
                null,
                null
            )},
            {new TypeStringTestCase(
                "org.foo:fooget:1.0.0",
                false,
                "BadRequestException",
                "Invalid type 'group' parameter"
            )},
            {new TypeStringTestCase(
                "ga4gh refget 1.0.0",
                false,
                "BadRequestException",
                "Could not instantiate ServiceType from 'type' string"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testServiceTypeNoArgsConstructor(TestCase testCase) throws Exception {

        ServiceType serviceType = new ServiceType();
        serviceType.setGroup(testCase.getGroup());
        serviceType.setArtifact(testCase.getArtifact());
        serviceType.setVersion(testCase.getVersion());
        Assert.assertEquals(serviceType.getGroup(), testCase.getGroup());
        Assert.assertEquals(serviceType.getArtifact(), testCase.getArtifact());
        Assert.assertEquals(serviceType.getVersion(), testCase.getVersion());
        Assert.assertEquals(serviceType.toString(), testCase.getExpString());
        Assert.assertEquals(serviceType.hashCode(), testCase.getExpHashcode());
        Assert.assertTrue(serviceType.equals(serviceType));
    }

    @Test(dataProvider = "cases")
    public void testServiceTypeAllArgsConstructor(TestCase testCase) throws Exception {

        ServiceType serviceType = new ServiceType(testCase.getGroup(), testCase.getArtifact(), testCase.getVersion());
        Assert.assertEquals(serviceType.getGroup(), testCase.getGroup());
        Assert.assertEquals(serviceType.getArtifact(), testCase.getArtifact());
        Assert.assertEquals(serviceType.getVersion(), testCase.getVersion());
        Assert.assertEquals(serviceType.toString(), testCase.getExpString());
        Assert.assertEquals(serviceType.hashCode(), testCase.getExpHashcode());
        Assert.assertTrue(serviceType.equals(serviceType));
    }

    @Test(dataProvider = "typeStringCases")
    public void testServiceTypeConstructorTypeString(TypeStringTestCase testCase) throws Exception {

        try {
            ServiceType serviceType = new ServiceType(testCase.getTypeString());
            String[] groupArtifactVersion = testCase.getTypeString().split(":");
            Assert.assertEquals(serviceType.getGroup(), groupArtifactVersion[0]);
            Assert.assertEquals(serviceType.getArtifact(), groupArtifactVersion[1]);
            Assert.assertEquals(serviceType.getVersion(), groupArtifactVersion[2]);
        } catch (BadRequestException ex) {
            if (testCase.getExpSuccess()) {
                throw new Exception("Instantiation by type string was supposed to succeed, but failed");
            } else {
                Assert.assertEquals(ex.getClass().getSimpleName(), testCase.getExpExceptionClass());
                Assert.assertEquals(ex.getMessage(), testCase.getExpExceptionMessage());
            }
        }
    }
}