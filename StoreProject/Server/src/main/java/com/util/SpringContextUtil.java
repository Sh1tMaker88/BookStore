package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringContextUtil {
    private static ApplicationContext instance;

    private SpringContextUtil() {

    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ClassPathXmlApplicationContext("appContext.xml");
        }
        return instance;
    }
}
