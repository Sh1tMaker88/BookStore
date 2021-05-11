package com.util;

import com.configuration.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringContextUtil {
    private static ApplicationContext instance;

    private SpringContextUtil() {

    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new AnnotationConfigApplicationContext(SpringConfig.class);
        }
        return instance;
    }
}
