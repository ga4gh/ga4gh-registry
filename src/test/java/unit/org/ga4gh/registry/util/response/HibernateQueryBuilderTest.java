package unit.org.ga4gh.registry.util.response;

import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.util.response.HibernateQueryBuilder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HibernateQueryBuilderTest {

    private class TestCase {

        private Class<?> typeClass;
        private String expected;
        private String[] joins;
        private String[][] filters;

        public TestCase(Class<?> typeClass, String[] joins, String[][] filters,
            String expected) {
            this.typeClass = typeClass;
            this.joins = joins;
            this.filters = filters;
            this.expected = expected;
        }

        public Class<?> getTypeClass() {
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
                + "JOIN FETCH a.releaseStatus "
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
                + "JOIN FETCH a.standardVersion "
                + "JOIN FETCH a.implementationCategory "
                + "JOIN FETCH a.organization "
                + "WHERE a.implementationCategory.category='APIService'"
            )}
        };
    }
    
    @Test(dataProvider = "cases")
    public void testQueryStringBuild(TestCase testCase) {

        HibernateQueryBuilder<?> builder = new HibernateQueryBuilder<>(testCase.getTypeClass());

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
}