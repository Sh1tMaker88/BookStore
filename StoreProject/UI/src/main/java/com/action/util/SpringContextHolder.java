package com.action.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder {
    private static ApplicationContext context;

    @Autowired
    public SpringContextHolder(ApplicationContext context) {
        SpringContextHolder.context = context;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
