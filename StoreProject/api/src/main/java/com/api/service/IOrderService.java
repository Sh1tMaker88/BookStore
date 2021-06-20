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

    void saveOrder(Order order);

    void deleteOrder(Long id);

    Order addOrder(String customerName, List<Book> books);

    Order addOrderUsingId(String customerName, List<Long> booksId);

    Order cancelOrder(Long orderId);

    Order changeOrderStatus(Long orderId, OrderStatus status);

    Double priceGetByPeriodOfTime(LocalDate fromDate, LocalDate tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDate fromDate, LocalDate tillDate);

    Order showDetails(Long orderId);

    List<Order> sortOrdersBy(OrderSort orderSort);
}
