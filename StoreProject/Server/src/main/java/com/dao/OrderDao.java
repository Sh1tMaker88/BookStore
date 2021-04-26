package com.dao;

import com.annotations.Singleton;
import com.api.dao.IOrderDao;
import com.model.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    public OrderDao() {
    }

//    @Override
//    public Order update(Order entity) {
//        Order order = getById(entity.getId());
//        order.setStatus(entity.getStatus());
//        order.setBooks(entity.getBooks());
//        order.setTotalPrice(entity.totalPrice(entity.getBooks()));
//        return order;
//    }

    //todo imp methods
    @Override
    protected String getInsertQuery() {
        return null;
    }

    @Override
    protected String getGetAllQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getCountOfObjectsQuery() {
        return null;
    }

    @Override
    protected void preparedStatementForCreate(PreparedStatement statement, Order entity) throws SQLException {

    }

    @Override
    protected void preparedStatementForUpdate(PreparedStatement statement, Order entity) throws SQLException {

    }

    @Override
    protected String getTableName() {
        return null;
    }
}
