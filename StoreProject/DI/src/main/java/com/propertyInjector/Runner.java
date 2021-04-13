package com.propertyInjector;

import com.interfaces.IConfig;

import java.util.Map;

public class Runner {
    public static ApplicationContext run(String path, Map<Class, Class> ifc2ImplClass) {
        Config config = new Config(path, ifc2ImplClass);
        ApplicationContext context = ApplicationContext.getInstance();
        context.setConfig(config);
        ObjectCreator creator = new ObjectCreator(context);
        context.setCreator(creator);

        return context;
    }
}
