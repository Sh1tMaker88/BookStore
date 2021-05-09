package com.dao;

import com.api.dao.GenericDao;
import com.exception.DaoException;
import com.model.AIdentity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractDao<T extends AIdentity> implements GenericDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class.getName());
    SessionFactory factory;

    public AbstractDao() {
        try {
            this.factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            LOGGER.warn("Cannot configure factory", e);
        }
    }

    @Override
    public T create(T entity) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        T t = getById(entity.getId());
        if (t == null) {
            throw new DaoException("Create entity failed");
        }
        LOGGER.info(t + " has been created");
        session.close();
        return t;
    }

    @Override
    public T getById(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();
        T t = session.get(getClazz(), id);
        if (t == null) {
            LOGGER.warn("No such ID=" + id);
            throw new DaoException("No such id=" + id);
        }
        session.getTransaction().commit();
        session.close();
        return t;

    }

    //todo configure it to use with lazy init
    @Override
    public List<T> getAll() {
        try {
            Session repSession = factory.openSession();
            repSession.beginTransaction();
            String query = String.format("FROM %s", getClassName());
            List<T> list = (List<T>) repSession.createQuery(query).list();
//            String s = (list.toString());
            repSession.close();
            return list;
        } catch (HibernateException e) {
            LOGGER.warn("GetAll failed");
            throw new DaoException("GetAll failed", e);
        }
    }

    @Override
    public void delete(T entity) {
        Session session = factory.openSession();
        session.beginTransaction();
        T t = getById(entity.getId());
        if (t == null) {
            throw new DaoException("No such entity in data base, delete failed");
        }
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
        LOGGER.info(t + " has been deleted");
    }


    //todo use dynamic update , use merge
    @Override
    public T update(T entity) {
        Session session = factory.openSession();
        session.beginTransaction();
        T t = (T) session.merge(entity);
        session.getTransaction().commit();
        LOGGER.info(t + " has been updated");
        session.close();
        return t;

    }

    protected abstract String getClassName();

    protected abstract Class<T> getClazz();
}
