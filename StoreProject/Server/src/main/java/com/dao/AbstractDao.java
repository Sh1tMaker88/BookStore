package com.dao;

import com.api.dao.GenericDao;
import com.dao.util.Connector;
import com.dao.util.ResultSetToObject;
import com.exception.DaoException;
import com.model.AIdentity;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends AIdentity> implements GenericDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class.getName());
    private final Connector connector;
    private List<T> dataFromDB = new ArrayList<>();

    public AbstractDao() {
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
    }

    @Override
    public T create(T entity) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.openSession();
            session.beginTransaction();
            T t = (T) session.save(entity);
            session.getTransaction().commit();
            session.close();
            return t;
        }


//        Connection connection = connector.getConnection();
//        String sql = getInsertQuery();
//        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatementForCreate(statement, entity);
//            statement.executeUpdate();
//            ResultSet resultSet = statement.getGeneratedKeys();
//            resultSet.next();
//            Long entityId = resultSet.getLong(1);
//            LOGGER.info("Created " + getTableName() + " id:" + entityId);
//            entity.setId(entityId);
//            return entity;
//        } catch (SQLException e) {
//            LOGGER.warn("Create method failed", e);
//            throw new DaoException("Creation failed");
//        }
    }

    @Override
    public T getById(Long id) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.openSession();
            session.beginTransaction();
            T t = (T) session.get(Object.class, id);
            if (t == null) {
                LOGGER.warn("No such ID=" + id);
                throw new DaoException("No such id=" + id);
            }
            session.getTransaction().commit();
            session.close();
            return t;
        }

//
//        String sql;
//        if (getTableName().equalsIgnoreCase("order")) {
//            String s = "bookstore." + getTableName();
//            sql = String.format("SELECT * FROM %s JOIN order_book ON %s.id=order_book.order_id WHERE id=?"
//            ,s, s);
//        } else {
//            sql = String.format("SELECT * FROM %s WHERE id=?", getTableName());
//        }
//        Connection connection = connector.getConnection();
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (!resultSet.next()){
//                LOGGER.warn("No such ID=" + id);
//                throw new DaoException("No such id=" + id);
//            }
//            return (T) ResultSetToObject.parseResultSet(resultSet, getTableName());
//        } catch (SQLException e) {
//            LOGGER.warn(e.getMessage());
//            throw new DaoException(e);
//        }
    }

    @Override
    public List<T> getAll() {
        try (SessionFactory factory = new Configuration()
                .configure()
                .buildSessionFactory()) {
            Session session = factory.openSession();
            session.beginTransaction();
            String query = String.format("FROM %s", getClassName());
            dataFromDB = (List<T>) session.createQuery(query).getResultList();
            session.close();
            return dataFromDB;
        }
//
//        Connection connection = connector.getConnection();
//        String sql = getGetAllQuery();
//        String sql_count = getCountOfObjectsQuery();
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(sql_count);
//            resultSet.next();
//            if (dataFromDB.size() != resultSet.getInt(1)) {
//                try (PreparedStatement statement2 = connection.prepareStatement(sql)) {
//                    ResultSet resGetAll = statement2.executeQuery(sql);
//                    dataFromDB.clear();
//                    while (resGetAll.next()) {
//                        Object obj = ResultSetToObject.parseResultSet(resGetAll, getTableName());
//                        if (!dataFromDB.contains((T) obj)) {
//                            dataFromDB.add((T)obj);
//                        }
//                    }
//                    resGetAll.close();
//                    return new ArrayList<>(dataFromDB);
//                }
//            } else {
//                resultSet.close();
//                return new ArrayList<>(dataFromDB);
//            }
//        } catch (SQLException e) {
//            LOGGER.warn("GetAll method failed", e);
//            throw new DaoException(e);
//        }
    }



    @Override
    public void delete(T entity) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.openSession();
            session.beginTransaction();
            T t = getById(entity.getId());
            if (t == null) {
                throw new DaoException("No such entity in data base");
            }
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            LOGGER.info(t + " has been deleted");
        }

//        Connection connection = connector.getConnection();
//        String sql = getDeleteQuery();
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setLong(1, entity.getId());
//            int resultSet = statement.executeUpdate();
//            if (resultSet == 1) {
//                LOGGER.info(entity + " has been deleted");
//                dataFromDB.remove(entity);
//            } else {
//                throw new DaoException("Delete failed");
//            }
//        } catch (SQLException e) {
//            LOGGER.warn("Delete method failed", e);
//            throw new DaoException(e);
//        }
    }

    @Override
    public T update(T entity) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.openSession();
            session.beginTransaction();
            T entityToUpdate = getById(entity.getId());
            if (entityToUpdate == null) {
                throw new DaoException("No such entity in data base");
            }
            entityToUpdate = updateEntityFields(entityToUpdate, entity);
            session.getTransaction().commit();
            session.close();
            return entityToUpdate;
        }

//        if (getById(entity.getId()) == null) {
//            throw new DaoException("No such entity");
//        }
//        Connection connection = connector.getConnection();
//        String sql = getUpdateQuery();
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            preparedStatementForUpdate(statement, entity);
//            int resultSet = statement.executeUpdate();
//            if (resultSet == 1) {
//                LOGGER.info(entity + " has been updated");
//            } else {
//                throw new DaoException("Update failed");
//            }
//            return entity;
//        } catch (SQLException e) {
//            LOGGER.warn(e.getMessage());
//            throw new DaoException(e);
//        }
    }

    protected abstract T updateEntityFields(T entityToUpdate, T entity);

    protected abstract String getClassName();

//    protected abstract String getInsertQuery();
//
//    protected abstract String getGetAllQuery();
//
//    protected abstract String getDeleteQuery();
//
//    protected abstract String getUpdateQuery();
//
//    protected abstract String getCountOfObjectsQuery();
//
//    protected abstract void preparedStatementForCreate(PreparedStatement statement, T entity) throws SQLException;
//
//    protected abstract void preparedStatementForUpdate(PreparedStatement statement, T entity) throws SQLException;
//
//    protected abstract String getTableName();
}
