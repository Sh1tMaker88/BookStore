package com.api.service;

import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.dao.IRequestDao;
import com.models.Book;
import com.models.Order;
import com.models.OrderStatus;
import com.service.OrderSort;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    IBookDao getBookDao();

    IOrderDao getOrderDao();

    IRequestDao getRequestDao();

    Order addOrder(String customerName, List<Book> books);

    void cancelOrder(Long orderId);

    void changeOrderStatus(Long orderId, OrderStatus status);

    double priceGetByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

//    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate, OrderSort orderSort);

    void showDetails(Order order);

    List<Order> sortOrdersBy(OrderSort orderSort);
}
