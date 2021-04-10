package com.propertyInjector;

import com.annotations.InjectValueFromProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyInjector {

    private static PropertyInjector instance;
    private static final Logger LOGGER = Logger.getLogger(PropertyInjector.class.getName());
    Set<Class<?>> classes = new HashSet<>();
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

    public void injectProperty() {
        //get fields with annotations
//        Class objClass = obj.getClass();
        classes = ClassScanner.getInstance().scanForClasses();
        if (!classes.isEmpty()) {
            for (Class<?> clas : classes) {
                for (Field field : clas.getDeclaredFields()) {
                    if (field.isAnnotationPresent(InjectValueFromProperties.class)) {
                        InjectValueFromProperties annotation = field.getAnnotation(InjectValueFromProperties.class);
                        //get properties as map if it needed
                        String path = PropertiesPath.valueOf(annotation.configName().toUpperCase()).getPath();
                        try {
                            getProperties(path);
                        } catch (IOException e) {
                            LOGGER.log(Level.WARNING, "No properties file to inject property", e);
                        }
                        String propValue = annotation.propertyName().isBlank() ? propertiesMap.get(field.getName().toLowerCase()) :
                                propertiesMap.get(annotation.propertyName().toLowerCase());
                        //if TYPE value of annotation is present
                        if (!annotation.type().isEmpty()) {
                            SetValue.castFieldAndSetValue(clas, field, annotation.type(), propValue);
                        } else /*if TYPE value of annotation isn't set*/ {
                            if (field.getType().isPrimitive()) {
                                SetValue.castFieldAndSetValue(clas, field, field.getType().toString(), propValue);
                            } else {
                                SetValue.castFieldAndSetValue(clas, field, "String", propValue);
                            }
                        }
                    }
                }
            }
        }
    }

    private void getProperties(String path) throws IOException {
//        pathToProperties = ClassLoader.getSystemClassLoader().
//                getResource(path).getPath();
        if (!path.equals(pathToProperties)) {
            pathToProperties = path;
            BufferedReader reader = new BufferedReader(new FileReader(pathToProperties));
            while (reader.ready()) {
                String[] prop = reader.readLine().split("=");
                propertiesMap.put(prop[0].replace("_", "").toLowerCase().trim(), prop[1].trim());
            }
        }
    }
}
