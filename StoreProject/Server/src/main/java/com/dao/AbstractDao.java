package com.dao;

import com.api.dao.GenericDao;
import com.dao.util.Connector;
import com.dao.util.ResultSetToObject;
import com.exception.DaoException;
import com.model.AIdentity;
import com.model.Order;
import com.propertyInjector.ApplicationContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//todo close statements in finally block
public abstract class AbstractDao<T extends AIdentity> implements GenericDao<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());
    private final Connector connector;
    private List<T> dataFromDB = new ArrayList<>();

    public AbstractDao() {
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
    }

    @Override
    public T create(T entity) {
        Connection connection = connector.getConnection();
        String sql = getInsertQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            preparedStatementForCreate(statement, entity);
            int affected = statement.executeUpdate();
            LOGGER.log(Level.INFO, "Created " + getTableName() + ":" + affected);
            return entity;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new DaoException("Creation failed");
        }
    }

    @Override
    public T getById(Long id) {
        String sql;
        if (getTableName().equalsIgnoreCase("order")) {
            String s = "bookstore." + getTableName();
            sql = String.format("SELECT * FROM %s JOIN order_book ON %s.id=order_book.order_id WHERE id=?"
            ,s, s);
        } else {
            sql = String.format("SELECT * FROM %s WHERE id=?", getTableName());
        }
        Connection connection = connector.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (T) ResultSetToObject.parseResultSet(resultSet, getTableName());
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new DaoException("No such id=" + id);
        }
    }

    @Override
    public List<T> getAll() {
        Connection connection = connector.getConnection();
        String sql = getGetAllQuery();
        String sql_count = getCountOfObjectsQuery();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql_count);
            resultSet.next();
            if (dataFromDB.size() != resultSet.getInt(1)) {
                try (PreparedStatement statement2 = connection.prepareStatement(sql)) {
                    ResultSet resGetAll = statement2.executeQuery(sql);
                    dataFromDB.clear();
                    while (resGetAll.next()) {
                        Object obj = ResultSetToObject.parseResultSet(resGetAll, getTableName());
                        if (!dataFromDB.contains((T) obj)) {
                            dataFromDB.add((T)obj);
                        }
                    }
                    resGetAll.close();
                    return new ArrayList<>(dataFromDB);
                }
            } else {
                resultSet.close();
                return new ArrayList<>(dataFromDB);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(T entity) {
        Connection connection = connector.getConnection();
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getId());
            int resultSet = statement.executeUpdate();
            if (resultSet == 1) {
                LOGGER.log(Level.INFO, entity + " has been deleted");
                dataFromDB.remove(entity);
            } else {
                throw new DaoException("Delete failed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public T update(T entity) {
        if (getById(entity.getId()) == null) {
            throw new DaoException("No such entity");
        }
        Connection connection = connector.getConnection();
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            preparedStatementForUpdate(statement, entity);
            int resultSet = statement.executeUpdate();
            if (resultSet == 1) {
                LOGGER.log(Level.INFO, entity + " has been updated");
            } else {
                throw new DaoException("Update failed");
            }
            return entity;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new DaoException(e);
        }
    }

    protected abstract String getInsertQuery();

    protected abstract String getGetAllQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getCountOfObjectsQuery();

    protected abstract void preparedStatementForCreate(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void preparedStatementForUpdate(PreparedStatement statement, T entity) throws SQLException;

    protected abstract String getTableName();
}
