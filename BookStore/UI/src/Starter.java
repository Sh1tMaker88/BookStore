package src;

import serialization.Deserializator;
import serialization.Serializator;
import src.menu.MenuController;

import java.io.*;

public class Starter {
    public static void main(String[] args) throws IOException {

//        -Djava.util.logging.config.file=<file path>
//        Facade facade = Facade.getInstance();
//        System.out.println(facade.getOrderService().getOrderDao().getAll());
//        System.out.println("----------------");

        new Initializer();
        new Deserializator();
        MenuController.getInstance().run();
        new Serializator();
    }
}
