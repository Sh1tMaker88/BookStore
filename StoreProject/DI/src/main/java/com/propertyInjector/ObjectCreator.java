package com.propertyInjector;

import com.interfaces.IObjectConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectCreator {

    private final ApplicationContext context;
    private List<IObjectConfigurator> configurators = new ArrayList<>();

    public ObjectCreator(ApplicationContext context) {
        this.context = context;
        try {
            for (Class<? extends IObjectConfigurator> aClass : context.getConfig().getScanner()
                    .getSubTypesOf(IObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
        } catch (InstantiationException | InvocationTargetException |
                NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public <T> T createAndConfigure(Class<T> cls) {
        T t = null;
        try {
                t = cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
        T finalT = t;
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(finalT, context));

        return t;
    }
}
