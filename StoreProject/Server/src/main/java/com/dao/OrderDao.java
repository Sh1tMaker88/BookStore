package com.dao;

import com.annotations.Singleton;
import com.api.dao.IOrderDao;
import com.models.Order;

@Singleton
public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    public OrderDao() {
    }

    @Override
    public Order update(Order entity) {
        Order order = getById(entity.getId());
        order.setStatus(entity.getStatus());
        order.setBooks(entity.getBooks());
        order.setTotalPrice(entity.totalPrice(entity.getBooks()));
        return order;
    }
}
