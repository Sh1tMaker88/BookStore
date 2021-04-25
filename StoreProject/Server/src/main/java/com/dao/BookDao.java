package com.dao;

import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.models.Book;
import com.propertyInjector.ApplicationContext;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

@Singleton
public class BookDao extends AbstractDao<Book> implements IBookDao {

    private static final String INSERT_QUERY = "INSERT INTO book" +
            "(name, author, publish_year, page_number, isbn, price, status, description, arrival_date) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM book WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE book SET name=?, author=?, publish_year=?" +
            ", page_number=?, isbn=?, price=?, status=?, description=?, arrival_date=?, order_count=?" +
            " WHERE id=? ;";
    private static final String GET_ALL_QUERY = "SELECT * FROM book";
    private static final String GET_COUNT_OF_OBJECTS_QUERY = "SELECT COUNT(*) FROM book";
    private static final String TABLE_NAME = "BOOK";

    public BookDao() {
    }

    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
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
    public void preparedStatementForCreate(PreparedStatement statement, Book entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAuthor());
        statement.setInt(3, entity.getYearOfPublish());
        statement.setInt(4, entity.getPageNumber());
        statement.setString(5, entity.getIsbn());
        statement.setDouble(6, entity.getPrice());
        statement.setString(7, String.valueOf(entity.getBookStatus()));
        statement.setString(8, entity.getDescription());
        statement.setDate(9, Date.valueOf(entity.getArrivalDate()));
    }

    @Override
    public void preparedStatementForUpdate(PreparedStatement statement, Book entity) throws SQLException {
        preparedStatementForCreate(statement, entity);
        statement.setInt(10, entity.getOrderCount());
        statement.setLong(11, entity.getId());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
