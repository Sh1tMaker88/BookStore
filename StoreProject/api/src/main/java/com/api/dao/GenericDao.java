package com.api.dao;

import java.util.List;

public interface GenericDao<T> {

    T create(T entity);

    T getById(Long id);

    List<T> getAll();

    void delete(T entity);

    T update(T entity);
}
