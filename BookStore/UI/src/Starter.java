package src;

import src.menu.MenuController;

import java.io.*;

public class Starter {
    public static void main(String[] args) throws IOException {

        Facade facade = Facade.getInstance();
        new Initializer();

        System.out.println(facade.getOrderService().getOrderDao().getAll());
        System.out.println("----------------");


        MenuController.getInstance().run();
    }
}
