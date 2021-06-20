package com.api.dao;

import java.util.List;

public interface GenericDao<T> {

    void saveOrUpdate(T entity);

    T create(T entity);

    T getById(Long id);

    List<T> getAll();

    void delete(T entity);

    void delete(Long id);

    T update(T entity);
}
