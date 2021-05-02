package com;

import com.menu.MenuController;


public class Starter {
    public static void main(String[] args) {
        new Initializer();
        MenuController.getInstance().run();
    }
}
