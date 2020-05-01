package unit.org.ga4gh.registry.util;

import org.ga4gh.registry.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

public class HibernateUtilTest {

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testBuildSessionFactory() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Assert.assertNotNull(factory);
    }

    @Test
    public void testBuildSessionFactoryWithoutContext() {

        try {
            HibernateUtil hUtil = new HibernateUtil();
            hUtil.getClass();
            hUtil.buildSessionFactory();
            // HibernateUtil.buildSessionFactory();
        } catch(ExceptionInInitializerError ex) {
            System.out.println(ex);
        }
    }
}