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
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            T t = (T) session.save(entity);
            session.getTransaction().commit();
            if (t == null) {
                throw new DaoException("Create entity failed");
            }
            LOGGER.info(t + " has been created");
            return t;
        }
    }

    @Override
    public T getById(Long id) {
//        EntityManager entityManager;
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> query = builder.createQuery(getClass());
////        Root<T> root = query.from(getClass());
//        return entityManager.createQuery(query).getResultList();


        try (Session session = factory.openSession()) {
            session.beginTransaction();
            T t = session.get(getClazz(), id);
            if (t == null) {
                LOGGER.warn("No such ID=" + id);
                throw new DaoException("No such id=" + id);
            }
            session.getTransaction().commit();
            return t;
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            String query = String.format("FROM %s", getClassName());
            List<T> list = (List<T>) session.createQuery(query).list();
            return list;
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            T t = getById(entity.getId());
            if (t == null) {
                throw new DaoException("No such entity in data base, delete failed");
            }
            session.delete(entity);
            session.getTransaction().commit();
            LOGGER.info(t + " has been deleted");
        }
    }


    //todo use dynamic update
    @Override
    public T update(T entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            T entityToUpdate = getById(entity.getId());
            if (entityToUpdate == null) {
                throw new DaoException("No such entity in data base");
            }
            entityToUpdate = updateEntityFields(entityToUpdate, entity);
            session.update(entityToUpdate);
            session.getTransaction().commit();
            LOGGER.info(entityToUpdate + " has been updated");
            return entityToUpdate;
        }
    }

    protected abstract T updateEntityFields(T entityToUpdate, T entity);

    protected abstract String getClassName();

    protected abstract Class<T> getClazz();
}
