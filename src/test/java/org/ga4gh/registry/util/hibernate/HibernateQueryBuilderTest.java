package org.ga4gh.registry.util.hibernate;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.ga4gh.registry.testutils.annotations.RegistryTestFullContext;

@RegistryTestFullContext
public class HibernateQueryBuilderTest extends AbstractTestNGSpringContextTests {

    private class TestCase {

        private Class<? extends RegistryModel> typeClass;
        private String expected;
        private String[] joins;
        private String[][] filters;

        public TestCase(Class<? extends RegistryModel> typeClass, String[] joins, String[][] filters,
            String expected) {
            this.typeClass = typeClass;
            this.joins = joins;
            this.filters = filters;
            this.expected = expected;
        }

        public Class<? extends RegistryModel> getTypeClass() {
            return typeClass;
        }

        public String[] getJoins() {
            return joins;
        }

        public String[][] getFilters() {
            return filters;
        }

        public String getExpected() {
            return expected;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                Standard.class,
                new String[0],
                new String[0][0],
                "select distinct a from org.ga4gh.registry.model.Standard a "
            )},
            {new TestCase(
                Standard.class,
                new String[]{"releaseStatus"},
                new String[0][0],
                "select distinct a from org.ga4gh.registry.model.Standard a "
                + "LEFT JOIN FETCH a.releaseStatus "
            )},
            {new TestCase(
                Implementation.class,
                new String[]{
                    "standardVersion",
                    "implementationCategory",
                    "organization"
                },
                new String[][]{
                    {"implementationCategory.category", "APIService"}
                },
                "select distinct a from org.ga4gh.registry.model.Implementation a "
                + "LEFT JOIN FETCH a.standardVersion "
                + "LEFT JOIN FETCH a.implementationCategory "
                + "LEFT JOIN FETCH a.organization "
                + "WHERE a.implementationCategory.category='APIService'"
            )}
        };
    }
    
    @Test(dataProvider = "cases")
    public void testQueryStringBuild(TestCase testCase) {
        HibernateQueryBuilder builder = getContext().getBean(HibernateQueryBuilder.class);
        builder.setResponseClass(testCase.getTypeClass());

        if (testCase.getJoins().length > 0) {
            for (String s : testCase.getJoins()) {
                builder.join(s);
            }
        }

        if (testCase.getFilters().length > 0) {
            for (String[] s : testCase.getFilters()) {
                builder.filter(s[0], s[1]);
            }
        }

        Assert.assertEquals(builder.build(), testCase.getExpected());
    }

    public ApplicationContext getContext() {
        return this.applicationContext;
    }
}
