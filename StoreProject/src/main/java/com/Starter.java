package com;

import com.facade.Facade;
import com.menu.MenuController;
import com.models.Book;
import com.propertyInjector.ApplicationContext;


public class Starter {
    public static void main(String[] args) {
        new Initializer();
        MenuController.getInstance().run();
    }
}
