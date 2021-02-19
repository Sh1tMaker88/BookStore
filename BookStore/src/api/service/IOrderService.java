package api.service;

import models.Book;
import models.Order;
import models.OrderStatus;
import service.BookSort;
import service.OrderSort;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
    Order addOrder(String customerName, List<Book> books);

    void cancelOrder(int orderId);

    void changeOrderStatus(int orderId, OrderStatus status);

    double priceGetByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate, OrderSort orderSort);

    void showDetails(Order order);

    List<Order> sortOrdersBy(OrderSort orderSort);
}
