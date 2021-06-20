package com.api.service;

import com.model.Book;
import com.model.Order;
import com.model.OrderStatus;
import com.sorting.OrderSort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    List<Order> getAllOrders();

    Order getById(Long id);

    Order addOrder(String customerName, List<Book> books);

    void cancelOrder(Long orderId);

    void changeOrderStatus(Long orderId, OrderStatus status);

    double priceGetByPeriodOfTime(LocalDate fromDate, LocalDate tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDate fromDate, LocalDate tillDate);

    void showDetails(Long orderId);

    List<Order> sortOrdersBy(OrderSort orderSort);
}
