package com.propertyInjector;

import com.annotations.InjectValueFromProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertyInjectorTest {
    @InjectValueFromProperties(configName = "annotations.properties", propertyName = "closeRequestAfterAddingBook", type = "boolean")
    boolean closeRequestAfterAddingBook;
    @InjectValueFromProperties(configName = "annotations.properties", propertyName = "unsoldBookMonth", type = "int")
    int unsoldBookMonth;
    @InjectValueFromProperties(configName = "annotations.properties", propertyName = "nameHero", type = "String")
    String nameHero = "";

    private static PropertyInjectorTest instance;
    String pathToProperties = "";
    Map<String, String> propertiesMap = new HashMap<>();

    private PropertyInjectorTest() {
    }

    public static PropertyInjectorTest getInstance(){
        if (instance == null) {
            instance = new PropertyInjectorTest();
        }
        return instance;
    }

    public static void main(String[] args) throws IOException {
        PropertyInjectorTest prop = PropertyInjectorTest.getInstance();
        prop.injectProperty(prop);
        System.out.println(prop.closeRequestAfterAddingBook +  ", " + prop.unsoldBookMonth + ", " + prop.nameHero);
    }

    public void injectProperty(Object obj) throws IOException {
        //get fields with annotations
        Class objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()){
            if (field.isAnnotationPresent(InjectValueFromProperties.class)) {
                InjectValueFromProperties annotation = field.getAnnotation(InjectValueFromProperties.class);
                //get properties as map if it needed
                String[] path = pathToProperties.split("/");
                if (pathToProperties.isEmpty() || !path[path.length - 1].equals(annotation.configName())) {
                    pathToProperties = annotation.configName();
                    getProperties(annotation.configName());
                }
                //searching properties for this field in properties file and set it, if it correct
                for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                    if (entry.getKey().replace("_", "").equalsIgnoreCase(annotation.propertyName()) &&
                            field.getType().toGenericString().equals(annotation.type())) {
                        field.setAccessible(true);
                        try {
                            if (annotation.type().equals("boolean")) {
                                field.setBoolean(obj, Boolean.parseBoolean(entry.getValue()));
                            } else if (annotation.type().equals("int")) {
                                field.setInt(obj, Integer.parseInt(entry.getValue()));
                            } else if (annotation.type().equals("String")) {
                                System.out.println(entry.getValue());
                                field.set(obj, entry.getValue());
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void getProperties(String path) throws IOException {
        pathToProperties = ClassLoader.getSystemClassLoader().
                getResource(path).getPath();
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(pathToProperties));
        while (reader.ready()) {
            String[] prop = reader.readLine().split("=");
            propertiesMap.put(prop[0].trim(), prop[1].trim());
        }
    }
}
