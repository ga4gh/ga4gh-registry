package org.ga4gh.registry.util.requesthandler.utils;

import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class TypeFilterTest {

    @DataProvider(name = "cases")
    public Object[][] testTypeFilterCases() {
        return new Object[][] {
            {null, ""},
            {"org.ga4gh:refget:*", "a.standardVersion.standard.artifact='refget'"},
            {"org.ga4gh:rnaget:1.0.0", "a.standardVersion.standard.artifact='rnaget' AND a.standardVersion.versionNumber='1.0.0'"}
        };
    }

    @Test(dataProvider = "cases")
    public void testTypeFilter(String typeString, String expectedFilterHql) throws Exception {
        TypeFilter typeFilter = new TypeFilter();
        typeFilter.getClass();
        HibernateQueryBuilder qb = new HibernateQueryBuilder();
        TypeFilter.filter(typeString, qb);
        String filterHql = qb.getFilterBuffer().toString();
        Assert.assertEquals(filterHql, expectedFilterHql);
    }
}