package com.dao.util;

import com.models.AIdentity;
import com.models.Book;
import com.models.BookStatus;
import com.util.DateConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToObject {

    public static AIdentity parseResultSet(ResultSet resultSet, String tableName) {
        try {
            switch (tableName.toUpperCase()) {
                case "BOOK":
                    return createBook(resultSet);
//                    break;
//                case "ORDER":
//                    return createOrder(resultSet);
//                    break;
//                case "REQUEST":
//                    return createRequest(resultSet);
//                    break;
                default:
                    throw new RuntimeException("Unknown table name: " + tableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Parsing exception",e);
        }
    }

    private static Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setName(resultSet.getString("name"));
        book.setAuthor(resultSet.getString("author"));
        book.setYearOfPublish(resultSet.getInt("publish_year"));
        book.setPageNumber(resultSet.getInt("page_number"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPrice(resultSet.getDouble("price"));
        book.setBookStatus(BookStatus.valueOf(resultSet.getString("status")));
        book.setDescription(resultSet.getString("description"));
        book.setArrivalDate(DateConverter.asLocalDate(resultSet.getDate("arrival_date")));
        book.setOrderCount(resultSet.getInt("order_count"));
        return book;
    }
}
