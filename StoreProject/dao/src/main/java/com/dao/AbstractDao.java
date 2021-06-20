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

import javax.persistence.Query;
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
    public void saveOrUpdate(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
    }

    @Override
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
    public T getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(getQuery(getClassName()) + " WHERE o.id=(:entityId)");
        query.setParameter("entityId", id);
        T t = (T)query.getSingleResult();
//        if (t == null) {
//            LOGGER.warn("No such ID=" + id);
//            throw new DaoException("No such id=" + id);
//        }
        return t;
    }

    @Override
    public List<T> getAll() {
        try {
            Session repSession = sessionFactory.getCurrentSession();
            String query = getQuery(getClassName());
            List<T> list = (List<T>) repSession.createQuery(query).list();
            return list;
        } catch (HibernateException e) {
            LOGGER.warn("GetAll failed");
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        T t = getById(entity.getId());
        if (t == null) {
            throw new DaoException("No such entity in data base, delete failed");
        }
        session.delete(t);
        LOGGER.info(t + " has been deleted");
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        String q = String.format("DELETE FROM %s WHERE id=(:entityId)", getClassName());
        Query query = session.createQuery(q);
        query.setParameter("entityId", id);
        query.executeUpdate();
    }

    @Override
    public T update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        T t = (T) session.merge(entity);
//    Query query = session.createQuery("SELECT o FROM Order AS o JOIN FETCH o.books")
        LOGGER.info(t + " has been updated");
        return t;

    }

    private String getQuery(String column) {
        String query;
        switch (column) {
            case "Book":
                query = "SELECT o FROM Book AS o LEFT JOIN FETCH o.orders LEFT JOIN FETCH o.request";
                break;
            case "Order":
                query = "SELECT o FROM Order AS o LEFT JOIN FETCH o.books";
                break;
            case "Request":
                query = "SELECT o FROM Request AS o LEFT JOIN FETCH o.book book";
                break;
            default:
                query = "from Book";
        }
        return query;
    }

    protected abstract String getClassName();

    protected abstract Class<T> getClazz();
}
