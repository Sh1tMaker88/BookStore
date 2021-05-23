package com.menu;


import com.util.ConsoleScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    private static final Logger LOGGER = LogManager.getLogger(MenuController.class.getName());

    private final Builder builder;
    private final Navigator navigator;
    private boolean flag = true;

    @Autowired
    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        while (flag) {
            builder.buildMenu();
            flag = false;
        }
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
                int index = ConsoleScannerUtil.scanInt();
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
                LOGGER.warn("Incorrect input");
            }
        }
    }
}
