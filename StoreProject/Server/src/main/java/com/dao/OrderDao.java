package com.dao;

import com.annotations.Singleton;
import com.api.dao.IOrderDao;
import com.models.Order;

@Singleton
public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    //todo delete instance
//    private static OrderDao instance;

    public OrderDao() {
    }

//    public static OrderDao getInstance(){
//        if (instance == null) {
//            instance = new OrderDao();
//        }
//        return instance;
//    }

    @Override
    public Order update(Order entity) {
        Order order = getById(entity.getId());
        order.setStatus(entity.getStatus());
        order.setBooks(entity.getBooks());
        order.setTotalPrice(entity.totalPrice(entity.getBooks()));
        return order;
    }
}
