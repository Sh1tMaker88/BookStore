package com.dao;

import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.dao.util.Connector;
import com.exception.DaoException;
import com.model.Book;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

@Singleton
public class BookDao extends AbstractDao<Book> implements IBookDao {

    private static final Logger LOGGER = LogManager.getLogger(BookDao.class.getName());
    private Connector connector;
//    private static final String INSERT_QUERY = "INSERT INTO book" +
//            "(name, author, publish_year, page_number, isbn, price, status, description, arrival_date) " +
//            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
//    private static final String DELETE_QUERY = "DELETE FROM book WHERE id=?;";
//    private static final String UPDATE_QUERY = "UPDATE book SET name=?, author=?, publish_year=?" +
//            ", page_number=?, isbn=?, price=?, status=?, description=?, arrival_date=?, order_count=?" +
//            " WHERE id=? ;";
//    private static final String UPDATE_ORDER_COUNT_QUERY = "UPDATE book set order_count = order_count + 1 WHERE id=?;";
//    private static final String GET_ALL_QUERY = "SELECT * FROM book;";
//    private static final String GET_COUNT_OF_OBJECTS_QUERY = "SELECT COUNT(*) FROM book;";
//    private static final String TABLE_NAME = "BOOK";

    public BookDao() {
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
    }

    @Override
    protected Book updateEntityFields(Book entityToUpdate, Book entity) {
        entityToUpdate.setName(entity.getName());
        entityToUpdate.setAuthor(entity.getAuthor());
        entityToUpdate.setYearOfPublish(entity.getYearOfPublish());
        entityToUpdate.setPageNumber(entity.getPageNumber());
        entityToUpdate.setIsbn(entity.getIsbn());
        entityToUpdate.setPrice(entity.getPrice());
        entityToUpdate.setBookStatus(entity.getBookStatus());
        entityToUpdate.setDescription(entity.getDescription());
        entityToUpdate.setArrivalDate(entity.getArrivalDate());
        entityToUpdate.setOrderCount(entity.getOrderCount());
        return entityToUpdate;
    }

    @Override
    protected String getClassName() {
        return "Book";
    }

//    @Override
//    public void updateOrderCount(Book book) {
//        Connection connection = connector.getConnection();
//        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_COUNT_QUERY)) {
//            statement.setLong(1, book.getId());
//            statement.executeUpdate();
//            LOGGER.info("Update order count for book with id=" + book.getId());
//        } catch (SQLException e) {
//            LOGGER.warn(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

//    @Override
//    public String getInsertQuery() {
//        return INSERT_QUERY;
//    }
//
//    @Override
//    protected String getGetAllQuery() {
//        return GET_ALL_QUERY;
//    }
//
//    @Override
//    protected String getDeleteQuery() {
//        return DELETE_QUERY;
//    }
//
//    @Override
//    protected String getUpdateQuery() {
//        return UPDATE_QUERY;
//    }
//
//    @Override
//    protected String getCountOfObjectsQuery() {
//        return GET_COUNT_OF_OBJECTS_QUERY;
//    }
//
//    @Override
//    public void preparedStatementForCreate(PreparedStatement statement, Book entity) throws SQLException {
//        statement.setString(1, entity.getName());
//        statement.setString(2, entity.getAuthor());
//        statement.setInt(3, entity.getYearOfPublish());
//        statement.setInt(4, entity.getPageNumber());
//        statement.setString(5, entity.getIsbn());
//        statement.setDouble(6, entity.getPrice());
//        statement.setString(7, String.valueOf(entity.getBookStatus()));
//        statement.setString(8, entity.getDescription());
//        statement.setDate(9, Date.valueOf(entity.getArrivalDate()));
//    }
//
//    @Override
//    public void preparedStatementForUpdate(PreparedStatement statement, Book entity) throws SQLException {
//        preparedStatementForCreate(statement, entity);
//        statement.setInt(10, entity.getOrderCount());
//        statement.setLong(11, entity.getId());
//    }
//
//    @Override
//    protected String getTableName() {
//        return TABLE_NAME;
//    }
}
