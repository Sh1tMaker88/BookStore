package com.controller.webConfiguration;

import com.configuration.SpringPersistenceConfig;
import com.security.configuration.MySecurityConfig;
import com.security.configuration.MySecurityInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MyDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MySecurityConfig.class, SpringPersistenceConfig.class, SpringControllerConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
