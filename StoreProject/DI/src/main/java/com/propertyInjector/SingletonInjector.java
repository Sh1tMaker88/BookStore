package com.propertyInjector;

import com.annotations.InjectByType;
import com.annotations.InjectValueFromProperties;
import com.interfaces.IObjectConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonInjector implements IObjectConfigurator {

    private static final Logger LOGGER = Logger.getLogger(PropertyInjector.class.getName());
    private String pathToProperties = "";
    private Map<String, String> propertiesMap = new HashMap<>();

    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                InjectByType annotation = field.getAnnotation(InjectByType.class);
//                String path = PropertiesPath.valueOf(annotation.configName().toUpperCase()).getPath();
//                try {
//                    getProperties(path);
//                } catch (IOException e) {
//                    LOGGER.log(Level.WARNING, "No properties file to inject property", e);
//                }
                field.setAccessible(true);
                Object obj = context.getObject(field.getType());
                try {
                    field.set(t, obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getProperties(String path) throws IOException {
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
