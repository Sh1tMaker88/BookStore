package com.menu;


import com.action.ConsoleScanner;
import com.serialization.Serializator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MenuController {

    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());

    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;

    private MenuController() {
        builder = Builder.getInstance();
        builder.buildMenu();
        navigator = Navigator.getInstance();
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }


    public void run() {
        System.out.printf("%8s\nWelcome to menu\n%8s\n", "***", "***");
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        boolean flag = true;
        while (flag) {
            try {
                int index = ConsoleScanner.scanInt();
                if (index == 0) {
//                    new Serializator();
                    flag = false;
                    System.out.printf("%7s\nClosing menu\n%7s\n", "***", "***");
                } else if (index > 0 && index <= navigator.getCurrentMenu().getMenuItems().size()) {
                    navigator.navigate(index - 1);
                    navigator.printMenu();
                } else {
                    System.out.println("Wrong key, use numbers in menu only");
                }
            } catch (IOException | NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Incorrect input");
            }
        }
    }
}
