package com;

import com.configuration.SpringPersistenceConfig;
import com.menu.MenuController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Starter {
    public static void main(String[] args) {
//        new Initializer();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringPersistenceConfig.class);
//        MenuController menuController = context.getBean("menuController", MenuController.class);
//        menuController.run();
        context.getBean(MenuController.class).run();
    }
}
