package dao;

import api.dao.IOrderDao;
import models.Order;

import java.util.Objects;

public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    private static OrderDao instance;

    private OrderDao() {
    }

    public static OrderDao getInstance(){
        return Objects.requireNonNullElse(instance, new OrderDao());
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
