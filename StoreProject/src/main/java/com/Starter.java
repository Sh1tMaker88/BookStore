package com;

import com.menu.MenuController;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Starter {
    public static void main(String[] args) {
//        new Initializer();
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("appContext.xml");
        MenuController.getInstance().run();
    }
}
