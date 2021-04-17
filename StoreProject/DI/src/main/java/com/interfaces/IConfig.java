package com.interfaces;

import org.reflections.Reflections;

public interface IConfig {
    <T> Class<? extends T> getClassToImplement(Class<T> ifc);

    Reflections getScanner();
}
