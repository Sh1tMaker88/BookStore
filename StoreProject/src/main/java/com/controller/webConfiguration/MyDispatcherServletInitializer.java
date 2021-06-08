package com.controller.webConfiguration;

import com.configuration.SpringPersistenceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MyDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringPersistenceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringControllerConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
