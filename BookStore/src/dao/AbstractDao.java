package dao;

import api.dao.GenericDao;
import models.AIdentity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends AIdentity> implements GenericDao<T> {

    private List<T> list = new ArrayList<>();

    @Override
    public void create(T entity) {
        list.add(entity);
    }

    @Override
    public T getById(int id) {
        for (T entity : list){
            if (id == entity.getId()){
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    public void delete(T entity) {
        list.remove(entity);
    }

    @Override
    //todo
    public T update(T entity) {
        return null;
    }
}
