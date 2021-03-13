import menu.MenuController;
import propertyInjector.PropertyInjectorTest;
import serialization.*;
import service.BookService;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) throws IOException {

//        -Djava.util.logging.config.file=<file path>
//        facade.Facade facade = facade.Facade.getInstance();
//        System.out.println(facade.getOrderService().getOrderDao().getAll());
//        System.out.println("----------------");

        new Initializer();
        new Deserializator();
        MenuController.getInstance().run();
        new Serializator();
    }
}
