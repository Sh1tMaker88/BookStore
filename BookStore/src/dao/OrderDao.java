package dao;

import api.dao.IOrderDao;
import models.Order;

public class OrderDao extends AbstractDao<Order> implements IOrderDao {
    @Override
    public Order update(Order entity) {
        Order order = getById(entity.getId());
        order.setStatus(entity.getStatus());
        order.setBooks(entity.getBooks());
        order.setTotalPrice(entity.totalPrice(entity.getBooks()));
        return order;
    }
}
