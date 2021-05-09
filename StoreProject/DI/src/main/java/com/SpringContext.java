package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringContext {
    private static ApplicationContext instance;

    private SpringContext() {

    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ClassPathXmlApplicationContext("appContext.xml");
        }
        return instance;
    }
}
