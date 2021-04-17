package com;

import com.menu.MenuController;
import com.propertyInjector.ApplicationContext;
import com.serialization.*;


public class Starter {
    public static void main(String[] args) {
        new Initializer();
//        System.out.println(ApplicationContext.getInstance().getCache());
        MenuController.getInstance().run();
        new Serializator();
    }
}
