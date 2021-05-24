package com.anton.controller.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfigeration extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?> [] getRootConfigClasses() {
        return new Class[]{ContextConfiguration.class};
    }

    @Override
    protected Class<?> [] getServletConfigClasses() {
        return new Class[]{ServletConfig.class};
    }

    @Override
    protected String [] getServletMappings() {
        return new String[]{"/"};
    }
}
