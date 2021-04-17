package com.propertyInjector;

import com.annotations.ClassToInjectProperty;
import com.interfaces.IConfig;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config implements IConfig {
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private Reflections scanner;
    private Map<Class, Class> classToImplement;

    public Config(String path, Map<Class, Class> classToImplement) {
        this.scanner = new Reflections(path);
        this.classToImplement = classToImplement;
    }

    public Reflections getScanner() {
        return scanner;
    }

    public <T> Class<? extends T> getClassToImplement(Class<T> cls) {
        return classToImplement.computeIfAbsent(cls, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(cls);
            if (classes.size() != 1) {
                LOGGER.log(Level.WARNING, cls + "have more than one implementation, fix it");
            }
            return classes.iterator().next();
        });
    }
}
