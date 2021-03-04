package src.menu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

//singleton
public class MenuController {

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
        return Objects.requireNonNullElse(instance, new MenuController());
    }


    public void run() throws IOException {
        System.out.printf("%8s\nWelcome to menu\n%8s\n", "***", "***");
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        boolean flag = true;
        while (flag) {
            int index = Integer.parseInt(reader.readLine());
            if (index == 0){
                flag = false;
                System.out.printf("%7s\nClosing menu\n%7s\n", "***", "***");
            } else if (index > 0 && index <= navigator.getCurrentMenu().getMenuItems().size()){
                navigator.navigate(index - 1);
                navigator.printMenu();
            } else {
                System.out.println("Out of size, use numbers in menu only");
            }
        }
    }
}
