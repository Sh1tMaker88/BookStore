package com.dao;

import com.annotations.Singleton;
import com.api.dao.IOrderDao;
import com.dao.util.Connector;
import com.exception.DaoException;
import com.model.Order;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

@Singleton
public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDao.class.getName());
    private Connector connector;

    private static final String INSERT_ORDER_QUERY = "INSERT INTO bookstore.order (customer_name, status, order_date) " +
            "VALUES(?, ?, ?);";
    private static final String INSERT_ORDER_BOOK_QUERY = "INSERT INTO order_book (order_id, book_id) VALUES # ;";
    private static final String SET_ORDER_COST_QUERY = "UPDATE bookstore.order SET price=(SELECT SUM(book.price) " +
            "FROM order_book JOIN book ON book.id=order_book.book_id WHERE order_id=?) WHERE id=?;";
    private static final String DELETE_QUERY = "DELETE FROM bookstore.order WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE bookstore.order " +
            "SET customer_name=?, status=?, order_date=?, date_of_done=? WHERE id=? ;";
    private static final String UPDATE_DATE_OF_DONE_QUERY = "UPDATE bookstore.order SET date_of_done=? WHERE id=?;";
    private static final String GET_ALL_QUERY = "SELECT * FROM bookstore.order JOIN order_book " +
            "ON order_book.order_id = bookstore.order.id;";
    private static final String GET_COUNT_OF_OBJECTS_QUERY = "SELECT COUNT(*) FROM bookstore.order;";
    private static final String TABLE_NAME = "ORDER";

    public OrderDao() {
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
    }

    @Override
    public Order createOrder(Order entity) {
        Connection connection = connector.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatementForCreate(statement, entity);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Long generatedId;
            if (resultSet.next()) {
                generatedId = resultSet.getLong(1);
            } else {
                throw new DaoException("Creation failed");
            }
            LOGGER.info("Created " + getTableName() + " id=" + generatedId);
            statement.close();
            String sql = OrderDao.getInsertQuery(entity);
            PreparedStatement statement2 = connection.prepareStatement(sql);
            preparedStatementForCreateOrder_book(statement2, entity, generatedId);
            statement2.executeUpdate();
            LOGGER.info("Added books for order");
            statement2.close();
            PreparedStatement statement3 = connection.prepareStatement(SET_ORDER_COST_QUERY);
            statement3.setLong(1, generatedId);
            statement3.setLong(2, generatedId);
            statement3.executeUpdate();
            LOGGER.info("Set price for order");
            statement3.close();
            return entity;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            throw new DaoException("Creation failed");
        }
    }

    private static String getInsertQuery(Order entity) {
        String insert = INSERT_ORDER_BOOK_QUERY;
        for (int i = 0; i < entity.getBooksId().size(); i++) {
            insert = (i == entity.getBooksId().size() - 1)
                    ? insert.replace("#", "(?, ?)")
                    : insert.replace("#", "(?, ?), #");
        }
        return insert;
    }

    private void preparedStatementForCreateOrder_book(PreparedStatement statement, Order entity
            , Long orderId) throws SQLException {
        int i = 1;
        for (Long book_id : entity.getBooksId()) {
            statement.setLong(i, orderId);
            i++;
            statement.setLong(i, book_id);
            i++;
        }
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_ORDER_QUERY;
    }

    @Override
    protected String getGetAllQuery() {
        return GET_ALL_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getCountOfObjectsQuery() {
        return GET_COUNT_OF_OBJECTS_QUERY;
    }

    @Override
    protected void preparedStatementForCreate(PreparedStatement statement, Order entity) throws SQLException {
        statement.setString(1, entity.getCustomerName());
        statement.setString(2, String.valueOf(entity.getStatus()));
        statement.setTimestamp(3, Timestamp.valueOf(entity.getOrderDate()));
    }

    @Override
    protected void preparedStatementForUpdate(PreparedStatement statement, Order entity) throws SQLException {
        statement.setString(1, entity.getCustomerName());
        statement.setString(2, String.valueOf(entity.getStatus()));
        statement.setTimestamp(3, Timestamp.valueOf(entity.getOrderDate()));
        if (entity.getDateOfDone() != null) {
            statement.setTimestamp(4, Timestamp.valueOf(entity.getDateOfDone()));
        } else {
            statement.setTimestamp(4, null);
        }
        statement.setLong(5, entity.getId());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
