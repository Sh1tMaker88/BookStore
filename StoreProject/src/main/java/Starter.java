import com.annotations.ClassToInjectProperty;
import com.menu.MenuController;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.serialization.*;

import java.util.Set;

public class Starter {
    public static void main(String[] args) {

//        Reflections reflections = Reflections.collect();

        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com")));
//        Reflections reflections = new Reflections(BookService.class.getClassLoader());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ClassToInjectProperty.class);
        System.out.println(classes);

        new Initializer();
        new Deserializator();
        MenuController.getInstance().run();
        new Serializator();
    }
}