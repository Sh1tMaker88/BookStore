import annotations.ClassToInjectProperty;
import dao.AbstractDao;
import menu.MenuController;
import org.reflections.Reflections;
import serialization.*;
import service.BookService;

import java.util.Set;

public class Starter {
    public static void main(String[] args) {

//        -Djava.util.logging.config.file=<file path>
//        facade.Facade facade = facade.Facade.getInstance();
//        System.out.println(facade.getOrderService().getOrderDao().getAll());
//        System.out.println("----------------");

//        Reflections reflections = Reflections.collect();

//        new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(packageToScan)))
        Reflections reflections = new Reflections(AbstractDao.class.getClassLoader());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ClassToInjectProperty.class);
        System.out.println(classes);

        new Initializer();
        new Deserializator();
        MenuController.getInstance().run();
        new Serializator();
    }
}