package com.propertyInjector;

import com.annotations.ClassToInjectProperty;
import org.reflections.Reflections;

import java.util.Set;

public class ClassScanner {
    private static ClassScanner instance;

    private ClassScanner(){

    }

    public static ClassScanner getInstance(){
        if (instance == null) {
            instance = new ClassScanner();
        }
        return instance;
    }

    public Set<Class<?>> scanForClasses() {
//        Reflections reflections = new Reflections(new ConfigurationBuilder().
//                setUrls(ClasspathHelper.forPackage("com")));
        Reflections reflections = new Reflections("com");
        return reflections.getTypesAnnotatedWith(ClassToInjectProperty.class);
    }
}
