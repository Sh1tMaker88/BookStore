package propertyInjector;

import annotations.InjectValueFromProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertyInjector {

    private static PropertyInjector instance;
    String pathToProperties = "";
    Map<String, String> propertiesMap = new HashMap<>();

    private PropertyInjector() {
    }

    public static PropertyInjector getInstance() {
        if (instance == null) {
            instance = new PropertyInjector();
        }
        return instance;
    }

    public void injectProperty(Object obj) throws IOException {
        //get fields with annotations
        Class objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectValueFromProperties.class)) {
                InjectValueFromProperties annotation = field.getAnnotation(InjectValueFromProperties.class);
                //get properties as map if it needed
                String path = PropertiesPath.valueOf(annotation.configName().toUpperCase()).getPath();
                if (!path.equals(pathToProperties)) {
                    pathToProperties = path;
                    getProperties();
                }
                String propValue = annotation.propertyName().isBlank() ? propertiesMap.get(field.getName().toLowerCase()) :
                        propertiesMap.get(annotation.propertyName().toLowerCase());
                //if TYPE value of annotation is present
                if (!annotation.type().isEmpty()) {
                    SetValue.castFieldAndSetValue(obj, field, annotation.type(), propValue);
                } else /*if TYPE value of annotation isn't set*/ {
                    if (field.getType().isPrimitive()) {
                        SetValue.castFieldAndSetValue(obj, field, field.getType().toString(), propValue);
                    } else {
                        SetValue.castFieldAndSetValue(obj, field, "String", propValue);
                    }
                }
            }
        }

    }

    private void getProperties() throws IOException {
//        pathToProperties = ClassLoader.getSystemClassLoader().
//                getResource(path).getPath();
        BufferedReader reader = new BufferedReader(new FileReader(pathToProperties));
        while (reader.ready()) {
            String[] prop = reader.readLine().split("=");
            propertiesMap.put(prop[0].replace("_", "").toLowerCase().trim(), prop[1].trim());
        }
    }
}
