package com;

import com.configuration.SpringConfig;
import com.menu.MenuController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Starter {
    public static void main(String[] args) {
//        new Initializer();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        MenuController.getInstance().run();
    }
}
