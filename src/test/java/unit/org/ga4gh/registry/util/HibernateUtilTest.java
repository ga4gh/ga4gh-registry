package unit.org.ga4gh.registry.util;

import org.ga4gh.registry.util.HibernateUtil;
import org.testng.annotations.Test;

public class HibernateUtilTest {

    @Test
    public void testBuildSessionFactoryWithoutContext() {

        try {
            HibernateUtil hUtil = new HibernateUtil();
            hUtil.getClass();
            hUtil.buildSessionFactory();
        } catch(ExceptionInInitializerError ex) {
            System.out.println(ex);
        }
    }
}
