package unit.org.ga4gh.registry.constant;

import org.ga4gh.registry.constant.Ids;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IdsTest {

    @Test
    public void idsTest() throws Exception {
        Ids ids = new Ids();
        ids.getClass();
        Assert.assertEquals(Ids.SELF_UUID, "7893404d-7b73-4983-9891-89023c8a34fa");
        Assert.assertEquals(Ids.STANDARD_UUID, "d37b54c1-e258-4828-a756-7b605afb626d");
        Assert.assertEquals(Ids.ORGANIZATION_UUID, "f3cfc0d2-e9bd-4b26-a856-0d2b2f47d1f4");
    }

}