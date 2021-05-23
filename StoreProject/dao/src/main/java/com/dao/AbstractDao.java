package com.dao;

import com.api.dao.GenericDao;
import com.exception.DaoException;
import com.model.AIdentity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public abstract class AbstractDao<T extends AIdentity> implements GenericDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class.getName());

    protected SessionFactory sessionFactory;

    public AbstractDao() {
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public T create(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        T t = getById(entity.getId());
        if (t == null) {
            throw new DaoException("Create entity failed");
        }
        LOGGER.info(t + " has been created");
        return t;
    }

    @Override
    @Transactional
    public T getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        T t = session.get(getClazz(), id);
        if (t == null) {
            LOGGER.warn("No such ID=" + id);
            throw new DaoException("No such id=" + id);
        }
        return t;
    }

    @Override
    @Transactional
    public List<T> getAll() {
        try {
            Session repSession = sessionFactory.getCurrentSession();
            String query = getClassName().equalsIgnoreCase("order") ?
                    String.format("FROM %s AS o JOIN FETCH o.books books", getClassName()) :
                    String.format("FROM %s", getClassName());
            List<T> list = (List<T>) repSession.createQuery(query).list();
            return list;
        } catch (HibernateException e) {
            LOGGER.warn("GetAll failed");
            throw new DaoException("GetAll failed", e);
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        T t = getById(entity.getId());
        if (t == null) {
            throw new DaoException("No such entity in data base, delete failed");
        }
        LOGGER.info(t + " has been deleted");
    }

    @Override
    @Transactional
    public T update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        T t = (T) session.merge(entity);
        LOGGER.info(t + " has been updated");
        return t;

    }

    protected abstract String getClassName();

    protected abstract Class<T> getClazz();
}
