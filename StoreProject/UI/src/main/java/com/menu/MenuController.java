package com.menu;


import com.util.ConsoleScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MenuController {

    private static final Logger LOGGER = LogManager.getLogger(MenuController.class.getName());

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
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%8s\nWelcome to menu\n%8s\n", "***", "***");
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        boolean flag = true;
        while (flag) {
            try {
                int index = ConsoleScanner.scanInt();
                if (index == 0) {
                    flag = false;
                    System.out.printf("%7s\nClosing menu\n%7s\n", "***", "***");
                } else if (index > 0 && index <= navigator.getCurrentMenu().getMenuItems().size()) {
                    navigator.navigate(index - 1);
                    navigator.printMenu();
                } else {
                    System.out.println("Wrong key, use numbers in menu only");
                }
            } catch ( NumberFormatException e) {
                LOGGER.warn("Incorrect input");            }
        }
    }
}
