package com.dao.util;

import com.dao.OrderDao;
import com.model.*;
import com.propertyInjector.ApplicationContext;
import com.util.DateConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetToObject {

    private static Map<Long, Order> orders = new HashMap<>();

    public static AIdentity parseResultSet(ResultSet resultSet, String tableName) {
        try {
            switch (tableName.toUpperCase()) {
                case "BOOK":
                    return createBook(resultSet);
                case "REQUEST":
                    return createRequest(resultSet);
                case "ORDER":
                    return createOrder(resultSet);
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

    private static Request createRequest(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getLong("id"));
        request.setBook_id(resultSet.getLong("book_id"));
        request.setRequestDate(DateConverter.asLocalDateTime(resultSet.getTimestamp("date")));
        request.setRequestStatus(RequestStatus.valueOf(resultSet.getString("status")));
        request.setRequestCount(resultSet.getInt("request_count"));
        return request;
    }

    //todo make it
    private static Order createOrder(ResultSet resultSet) throws SQLException {
        if (!orders.containsKey(resultSet.getLong("id"))) {
            Order order = new Order();
            order.setId(resultSet.getLong("id"));
            order.setCustomerName(resultSet.getString("customer_name"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            order.setOrderDate(DateConverter.asLocalDateTime(resultSet.getTimestamp("order_date")));
            if (resultSet.getTimestamp("date_of_done") != null) {
                order.setDateOfDone(DateConverter.asLocalDateTime(resultSet.getTimestamp("date_of_done")));
            }
            order.setTotalPrice(resultSet.getDouble("price"));
            order.addBookId(resultSet.getLong("order_book.book_id"));
            orders.put(order.getId(), order);
            return order;
        } else {
            Order order = orders.get(resultSet.getLong("id"));
            if (!order.getBooksId().contains(resultSet.getLong("order_book.book_id"))) {
                order.addBookId(resultSet.getLong("order_book.book_id"));
            }
            return order;
        }
    }
}
