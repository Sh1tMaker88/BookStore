package api.service;

import api.dao.IBookDao;
import api.dao.IOrderDao;
import api.dao.IRequestDao;
import models.Book;
import models.Order;
import models.OrderStatus;
import service.OrderSort;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    IBookDao getBookDao();

    IOrderDao getOrderDao();

    IRequestDao getRequestDao();

    Order addOrder(String customerName, List<Book> books);

    void cancelOrder(int orderId);

    void changeOrderStatus(int orderId, OrderStatus status);

    double priceGetByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate);

//    List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate, OrderSort orderSort);

    void showDetails(Order order);

    List<Order> sortOrdersBy(OrderSort orderSort);
}
