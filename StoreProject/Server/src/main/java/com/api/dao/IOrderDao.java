package com.api.dao;

import com.model.Book;
import com.model.Order;

import java.util.Set;


public interface IOrderDao extends GenericDao<Order> {
    Set<Book> getBooksThatAreNotBought(int monthToSetBookAsUnsold);
//    public Order createOrder(Order entity);
}
