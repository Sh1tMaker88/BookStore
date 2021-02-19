package service;

import api.dao.IOrderDao;
import api.dao.IRequestDao;
import api.service.IOrderService;
import models.*;
import util.IdGenerator;
import util.comparators.OrderDateOfDoneComparator;
import util.comparators.OrderPriceComparator;
import util.comparators.OrderStatusComparator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {

    private final IOrderDao orderDao;
    private final IRequestDao requestDao;

    public OrderService(IOrderDao orderDao, IRequestDao requestDao) {
        this.orderDao = orderDao;
        this.requestDao = requestDao;
    }

    @Override
    public Order addOrder(String customerName, List<Book> books) {
        Order order = new Order(customerName, books);
        order.setId(IdGenerator.generateOrderId());
        for (Book b : books){
            b.setOrderCount(b.getOrderCount() + 1);
            if (b.getBookStatus().equals(BookStatus.OUT_OF_STOCK)){
                Request request = new Request(b);
                request.setId(IdGenerator.generateRequestId());
                requestDao.create(request);
            }
        }
        orderDao.create(order);
        return order;
    }

    @Override
    public void cancelOrder(int orderId) {
        if (orderDao.getAll().stream().anyMatch(el->el.getId() == orderId)){
            Order order = orderDao.getById(orderId);
            order.setStatus(OrderStatus.CANCEL);

            //if order is cancelled decrease number that points how much this book has been ordered
            for (Book b : order.getBooks()){
                b.setOrderCount(b.getOrderCount() - 1);
            }
            orderDao.update(order);
            System.out.println("Order cancelled");
        } else {
            System.out.println("There is no such order");
        }
    }

    @Override
    public void changeOrderStatus(int orderId, OrderStatus status) {
        if (orderDao.getAll().stream().anyMatch(el->el.getId() == orderId)){
            Order order = orderDao.getById(orderId);
            order.setStatus(status);

            //if order is cancelled decrease number that points how much this book has been ordered
            if (order.getStatus().equals(OrderStatus.CANCEL)){
                for (Book b : order.getBooks()){
                    b.setOrderCount(b.getOrderCount() - 1);
                }
            }

            //if order is done we set date and time of the end of the order
            if (order.getStatus().equals(OrderStatus.DONE)){
                order.setDateOfDone(LocalDateTime.now());
            }
            orderDao.update(order);
            System.out.println("Order status changed to " + status);
        } else {
            System.out.println("There is no such order");
        }
    }

    @Override
    public double priceGetByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate) {
        List<Order> list = orderDao.getAll();
        double result = list.stream().filter(e-> e.getDateOfDone().isAfter(fromDate) && e.getDateOfDone().isBefore(tillDate))
                .filter(e->e.getStatus().equals(OrderStatus.DONE))
                .mapToDouble(Order::getTotalPrice)
                .sum();
        System.out.println("From " + fromDate + " till " + tillDate + " we earned ");
        return result;
    }

    @Override
    public List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate) {
        List<Order> list = orderDao.getAll();
        list = list.stream().filter(e-> e.getDateOfDone().isAfter(fromDate) && e.getDateOfDone().isBefore(tillDate))
                .filter(e->e.getStatus().equals(OrderStatus.DONE))
                .collect(Collectors.toList());
        System.out.println("From " + fromDate + " till " + tillDate + " were done orders: ");
        return list;
    }

    @Override
    public List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate, OrderSort orderSort) {
        List<Order> list = this.ordersDoneByPeriodOfTime(fromDate, tillDate);
        list = this.sortOrdersBy(orderSort);
        return list;
    }

    @Override
    public void showDetails(Order order) {
        System.out.println("Customer name: " + order.getCustomerName() + " and books he ordered: " + order.getBooks());
    }

    @Override
    public List<Order> sortOrdersBy(OrderSort orderSort) {
        List<Order> listToSort =  orderDao.getAll();
        switch (orderSort){
            case PRICE:
                listToSort.sort(new OrderPriceComparator());
                break;
            case STATUS:
                listToSort.sort(new OrderStatusComparator());
                break;
            case DATE_OF_DONE:
                listToSort.sort(new OrderDateOfDoneComparator());
        }
        return listToSort;
    }


}
