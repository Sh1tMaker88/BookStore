package com.api.service;

import com.model.Book;
import com.model.Order;
import com.model.OrderStatus;
import com.service.OrderSort;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    List<Order> getAllOrders();

    Order getById(Long id);

    Order addOrder(String customerName, List<Book> books);

    void cancelOrder(Long orderId);

    void changeOrderStatus(Long orderId, OrderStatus status);

    double priceGetByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

//    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate, OrderSort orderSort);

    void showDetails(Order order);

    List<Order> sortOrdersBy(OrderSort orderSort);
}
