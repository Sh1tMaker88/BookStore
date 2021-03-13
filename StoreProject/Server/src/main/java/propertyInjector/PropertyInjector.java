package propertyInjector;

import annotations.InjectValueFromProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertyInjector {
//    @InjectValueFromProperties(configName = "annotations.properties", propertyName = "closeRequestAfterAddingBook", type = "boolean")
//    boolean closeRequestAfterAddingBook;
//    @InjectValueFromProperties(configName = "annotations.properties", propertyName = "unsoldBookMonth", type = "int")
//    int unsoldBookMonth;
//    @InjectValueFromProperties(configName = "annotations.properties", propertyName = "nameHero", type = "String")
//    String nameHero = "";

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

    //crazy :(
    public void injectProperty(Object obj) throws IOException {
        //get fields with annotations
        Class objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectValueFromProperties.class)) {
                InjectValueFromProperties annotation = field.getAnnotation(InjectValueFromProperties.class);
                //get properties as map if it needed
                String string = PropertiesPath.valueOf(annotation.configName().toUpperCase()).getPath();
                if (!string.equals(pathToProperties)) {
                    pathToProperties = string;
                    getProperties();
                }
                //searching properties for this field in properties file and set it, if it correct
                for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                    if (entry.getKey().replace("_", "").equalsIgnoreCase(annotation.propertyName())) {
                        field.setAccessible(true);
                        try {
                            if (field.getType().isPrimitive()) {
                                switch (annotation.type().toLowerCase()) {
                                    case "boolean":
                                        field.setBoolean(obj, Boolean.parseBoolean(entry.getValue()));
                                        break;
                                    case "int":
                                    case "integer":
                                        field.setInt(obj, Integer.parseInt(entry.getValue()));
                                        break;
                                    case "double":
                                        field.setDouble(obj, Double.parseDouble(entry.getValue()));
                                }
                            } else {
                                String[] fieldType = field.getType().toString().split(".");
                                if (fieldType[fieldType.length - 1].equalsIgnoreCase(annotation.type())) {
                                    if (annotation.type().equals("String")) {
                                        field.set(obj, entry.getValue());
                                    }
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
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
            propertiesMap.put(prop[0].trim(), prop[1].trim());
        }
    }
}
