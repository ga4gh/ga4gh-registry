package org.ga4gh.registry.util.response;

import org.ga4gh.registry.model.Queryable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HibernateQuerierFactory implements ApplicationContextAware {

    private ApplicationContext context;

    private String beanNameSuffix = "HibernateQuerier";

    private String getBeanName(String beanNamePrefix) {
        return beanNamePrefix + beanNameSuffix;
    }

    @SuppressWarnings("unchecked")
    public HibernateQuerier<? extends Queryable> createHibernateQuerier(String beanNamePrefix) {
        return getContext().getBean(getBeanName(beanNamePrefix), HibernateQuerier.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }
}