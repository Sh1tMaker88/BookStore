package src.menu;

import java.util.Objects;
import java.util.Scanner;

//singleton
public class MenuController {

    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;

    private MenuController(){
        builder = Builder.getInstance();
        builder.buildMenu();
        navigator = Navigator.getInstance();
    }

    public static MenuController getInstance(){
        return Objects.requireNonNullElse(instance, new MenuController());
    }


    public void run(){
        System.out.println("Welcome to menu.");
        Scanner scanner = new Scanner(System.in);
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        boolean flag = true;
        while (flag) {
            int index = scanner.nextInt();
            if (index == 0){
                flag = false;
            }
            navigator.navigate(index);
            navigator.printMenu();
        }
    }
}
