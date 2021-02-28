package service;

import api.dao.IBookDao;
import api.dao.IOrderDao;
import api.dao.IRequestDao;
import api.service.IOrderService;
import dao.BookDao;
import dao.OrderDao;
import dao.RequestDao;
import exceptions.DaoException;
import exceptions.ServiceException;
import models.*;
import util.IdGenerator;
import util.comparators.OrderDateOfDoneComparator;
import util.comparators.OrderIdComparator;
import util.comparators.OrderPriceComparator;
import util.comparators.OrderStatusComparator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());
    private static OrderService instance;
    private final IBookDao bookDao;
    private final IOrderDao orderDao;
    private final IRequestDao requestDao;

    private OrderService() {
        bookDao = BookDao.getInstance();
        orderDao = OrderDao.getInstance();
        requestDao = RequestDao.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public IBookDao getBookDao() {
        return bookDao;
    }

    public IOrderDao getOrderDao() {
        return orderDao;
    }

    public IRequestDao getRequestDao() {
        return requestDao;
    }

    @Override
    public Order addOrder(String customerName, List<Book> books) {
        try {
            LOGGER.log(Level.INFO, "Generating order for customer '" + customerName + "'");
            Order order = new Order(customerName, books);
            order.setId(IdGenerator.generateOrderId());
            //increase number that points how much this book has been ordered
            for (Book b : books) {
                b.setOrderCount(b.getOrderCount() + 1);
                if (b.getBookStatus().equals(BookStatus.OUT_OF_STOCK)) {
                    LOGGER.log(Level.INFO, "Book with id=" + b.getId() + " is out of stock, adding request for it");
                    Request request = new Request(b);
                    request.setId(IdGenerator.generateRequestId());
                    requestDao.create(request);
                }
            }
            orderDao.create(order);
            return order;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addOrder failed", e);
            throw new ServiceException("Method addOrder failed", e);
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        try {
            LOGGER.log(Level.INFO, "Cancelling order with id=" + orderId);
            if (orderDao.getAll().stream().anyMatch(el -> el.getId() == orderId)) {
                Order order = orderDao.getById(orderId);
                order.setStatus(OrderStatus.CANCEL);

                //if order is cancelled decrease number that points how much this book has been ordered
                for (Book b : order.getBooks()) {
                    b.setOrderCount(b.getOrderCount() - 1);
                }
                orderDao.update(order);
                LOGGER.log(Level.INFO, "Order with id " + orderId + "cancelled");
            } else {
                LOGGER.log(Level.INFO, "There is no such order");
            }
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addOrder failed", e);
            throw new ServiceException("Method addOrder failed", e);
        }
    }

    @Override
    public void changeOrderStatus(int orderId, OrderStatus status) {
        try {
            LOGGER.log(Level.INFO, "Changing order status with id=" + orderId);
            if (orderDao.getAll().stream().anyMatch(el -> el.getId() == orderId)) {
                Order order = orderDao.getById(orderId);
                order.setStatus(status);
                //if order is cancelled decrease number that points how much this book has been ordered
                if (status.equals(OrderStatus.CANCEL)) {
                    for (Book b : order.getBooks()) {
                        b.setOrderCount(b.getOrderCount() - 1);
                    }
                }
                //if order is done we set date and time of the end of the order
                if (status.equals(OrderStatus.DONE)) {
                    order.setDateOfDone(LocalDateTime.now());
                }
                orderDao.update(order);
                LOGGER.log(Level.INFO, "Order with id=" + orderId + "has changed status to " + status);
            } else {
                LOGGER.log(Level.INFO, "There is no such order with id=" + orderId);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method changeOrderStatus failed", e);
            throw new ServiceException("Method changeOrderStatus failed", e);
        }

    }

    @Override
    public double priceGetByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate) {
        try {
            List<Order> list = orderDao.getAll();
            double result = list.stream().filter(e -> e.getDateOfDone().isAfter(fromDate) && e.getDateOfDone().isBefore(tillDate))
                    .filter(e -> e.getStatus().equals(OrderStatus.DONE))
                    .mapToDouble(Order::getTotalPrice)
                    .sum();
            LOGGER.log(Level.INFO, "From " + fromDate + " till " + tillDate + " we earned: " + result);
            return result;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method priceGetByPeriodOfTime failed", e);
            throw new ServiceException("Method priceGetByPeriodOfTime failed", e);
        }
    }

    @Override
    public List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate) {
        try {
            List<Order> list = orderDao.getAll();
            list = list.stream().filter(e -> e.getDateOfDone().isAfter(fromDate) && e.getDateOfDone().isBefore(tillDate))
                    .filter(e -> e.getStatus().equals(OrderStatus.DONE))
                    .collect(Collectors.toList());
            LOGGER.log(Level.INFO, "From " + fromDate + " till " + tillDate + " were done orders: \n" + list);
            return list;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method ordersDoneByPeriodOfTime failed", e);
            throw new ServiceException("Method ordersDoneByPeriodOfTime failed", e);
        }
    }

//    @Override
//    public List<Order> ordersDoneByPeriodOfTime(LocalDateTime fromDate, LocalDateTime tillDate, OrderSort orderSort) {
//        List<Order> list = this.ordersDoneByPeriodOfTime(fromDate, tillDate);
//        list = this.sortOrdersBy(orderSort);
//        return list;
//    }

    @Override
    public void showDetails(Order order) {
        System.out.println("Customer name: " + order.getCustomerName() + " and books he ordered: " + order.getBooks());
    }

    @Override
    public List<Order> sortOrdersBy(OrderSort orderSort) {
        List<Order> listToSort = orderDao.getAll();
        switch (orderSort) {
            case ID:
                listToSort.sort(new OrderIdComparator());
                break;
            case PRICE:
                listToSort.sort(new OrderPriceComparator());
                break;
            case STATUS:
                listToSort.sort(new OrderStatusComparator());
                break;
            case DATE_OF_DONE:
                listToSort.sort(new OrderDateOfDoneComparator());
                break;
            default:
                LOGGER.log(Level.WARNING, "No such type of sort");
                throw new ServiceException("Method sortOrdersBy failed");
        }
        return listToSort;
    }


}
