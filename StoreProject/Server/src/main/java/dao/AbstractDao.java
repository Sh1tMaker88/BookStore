package dao;

import api.dao.GenericDao;
import exceptions.DaoException;
import models.AIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDao<T extends AIdentity> implements GenericDao<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());
    private List<T> list = new ArrayList<>();

    @Override
    public void create(T entity) {
        list.add(entity);
    }

    @Override
    public T getById(Long id) {
        for (T entity : list) {
            if (id.equals(entity.getId())) {
                return entity;
            }
        }
        LOGGER.log(Level.WARNING, "There is no such id=" + id);
        throw new DaoException("No such id=" + id);
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
    public T update(T entity) {
        return entity;
    }
}
