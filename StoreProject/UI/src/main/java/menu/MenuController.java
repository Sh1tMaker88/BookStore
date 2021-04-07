package menu;


import service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

//singleton
public class MenuController {

    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());

    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private MenuController(){
        builder = Builder.getInstance();
        builder.buildMenu();
        navigator = Navigator.getInstance();
    }

    public static MenuController getInstance(){
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
            int index = -1;
            try {
                index = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Incorrect input");
            }
            if (index == 0){
                flag = false;
                System.out.printf("%7s\nClosing menu\n%7s\n", "***", "***");
            } else if (index > 0 && index <= navigator.getCurrentMenu().getMenuItems().size()){
                navigator.navigate(index - 1);
                navigator.printMenu();
            } else {
                System.out.println("Wrong key, use numbers in menu only");
            }
        }
    }
}
