package org.ga4gh.registry.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ImplementationCategoryTest {

    
    @Test
    public void testImplementationCategory() throws Exception {

        Implementation impl = new Implementation();
        impl.setCategory(ImplementationCategory.deployment);
        Assert.assertEquals(impl.getCategory(), ImplementationCategory.deployment);
        impl.setCategory(ImplementationCategory.implementation);
        Assert.assertEquals(impl.getCategory(), ImplementationCategory.implementation);
    }
}