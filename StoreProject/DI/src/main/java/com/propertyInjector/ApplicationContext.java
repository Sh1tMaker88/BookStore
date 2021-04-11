package com.propertyInjector;

import com.annotations.Singleton;
import com.interfaces.IConfig;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private Map<Class, Object> cache = new HashMap<>();
    private IConfig config;
    private ObjectCreator creator;

    public void setCreator(ObjectCreator creator) {
        this.creator = creator;
    }

    public ApplicationContext(IConfig config) {
        this.config = config;
    }

    public IConfig getConfig() {
        return config;
    }

    //cached singleton after creating it, if it already created - return it
    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getClassToImplement(type);
        }
        T t = creator.createAndConfigure(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }

        return t;
    }
}
