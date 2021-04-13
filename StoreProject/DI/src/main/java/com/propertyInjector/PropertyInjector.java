package com.propertyInjector;

import com.annotations.InjectValueFromProperties;
import com.interfaces.IObjectConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyInjector implements IObjectConfigurator {

    //todo delete instance
    private static PropertyInjector instance;
    private static final Logger LOGGER = Logger.getLogger(PropertyInjector.class.getName());
    private Set<Class<?>> classes = new HashSet<>();
    private String pathToProperties = "";
    private Map<String, String> propertiesMap = new HashMap<>();
    private ApplicationContext context;

    public PropertyInjector() {
    }

    public static PropertyInjector getInstance() {
        if (instance == null) {
            instance = new PropertyInjector();
        }
        return instance;
    }

    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectValueFromProperties.class)) {
                field.setAccessible(true);
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
//                Object obj = context.getObject(field.getType());
                if (!annotation.type().isEmpty()) {
                    SetValue.castFieldAndSetValue(t, field, annotation.type(), propValue);
                } else /*if TYPE value of annotation isn't set*/ {
                    if (field.getType().isPrimitive()) {
                        SetValue.castFieldAndSetValue(t, field, field.getType().toString(), propValue);
                    } else {
                        SetValue.castFieldAndSetValue(t, field, "String", propValue);
                    }
                }
            }
        }
    }

    public void injectProperty() {
        //get fields with annotations
//        Class objClass = obj.getClass();
        classes = ClassScanner.getInstance().scanForClasses();
        if (!classes.isEmpty()) {
            for (Class<?> cls : classes) {
                Object object = createObject(cls);
                for (Field field : object.getClass().getDeclaredFields()) {
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
                            SetValue.castFieldAndSetValue(object, field, annotation.type(), propValue);
                        } else /*if TYPE value of annotation isn't set*/ {
                            if (field.getType().isPrimitive()) {
                                SetValue.castFieldAndSetValue(object, field, field.getType().toString(), propValue);
                            } else {
                                SetValue.castFieldAndSetValue(object, field, "String", propValue);
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

    private <T> T createObject(Class<T> cls) {
        T t = null;
        try {
            if (cls.getDeclaredConstructor().getModifiers() == Modifier.PRIVATE) {
                Method method = cls.getMethod("getInstance");
                t = (T) method.invoke(cls);

            } else {
                t = cls.getDeclaredConstructor().newInstance();
            }
            Field field = t.getClass().getDeclaredField("instance");
            field.setAccessible(true);
            field.set(t, t);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return t;
    }

}
