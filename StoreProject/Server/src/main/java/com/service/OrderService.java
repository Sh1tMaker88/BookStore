package com.service;

import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.service.IOrderService;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.util.comparator.OrderDateOfDoneComparator;
import com.util.comparator.OrderIdComparator;
import com.util.comparator.OrderPriceComparator;
import com.util.comparator.OrderStatusComparator;
import com.model.Book;
import com.model.Order;
import com.model.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService implements IOrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class.getName());
    private final IBookDao bookDao;
    private final IOrderDao orderDao;

    @Autowired
    public OrderService(IBookDao bookDao, IOrderDao orderDao) {
        this.bookDao = bookDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

    @Override
    public Order getById(Long id) {
        return orderDao.getById(id);
    }

    @Override
    public Order addOrder(String customerName, List<Book> books) {
        try {
            LOGGER.info("Generating order for customer '" + customerName + "'");
            Order order = new Order(customerName, books);
            String s = order.toString();
            double totalPrice = 0.0;
            for (Book b : books) {
                Book book = bookDao.getById(b.getId());
                book.setOrderCount(book.getOrderCount() + 1);
                totalPrice = totalPrice + book.getPrice();
                bookDao.update(book);
            }
            order.setTotalPrice(totalPrice);
            orderDao.create(order);
            return order;
        } catch (DaoException e) {
            LOGGER.warn("Method addOrder failed", e);
            throw new ServiceException("Method addOrder failed", e);
        }
    }

    @Override
    public void cancelOrder(Long orderId) {
        try {
            Order order = getById(orderId);
            order.setStatus(OrderStatus.CANCEL);
            orderDao.update(order);
            LOGGER.info("Order with id=" + orderId + " cancelled");
            for (Book book : order.getBooks()) {
                book.setOrderCount(book.getOrderCount() - 1);
                bookDao.update(book);
            }
        } catch (HibernateException | DaoException e) {
            LOGGER.warn("Method cancelOrder failed", e);
            throw new ServiceException("Method cancelOrder failed", e);
        }

//        try (Session session = factory.openSession()) {
//        begin tranaction
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaUpdate<Order> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Order.class);
//            Root<Order> root = criteriaUpdate.from(Order.class);
//            var subquery = criteriaUpdate.subquery(Book.class).from(Book.class);
//            criteriaUpdate.set(root.get("status"), OrderStatus.CANCEL)
//                    .where(criteriaBuilder.equal(root.get("id"), orderId));
//
//
//            criteriaUpdate.set(subquery.get("orderCount")
//                    , Optional.ofNullable(criteriaBuilder.sum(subquery.get("orderCount"), 1)))
//                    .where(subquery.get("id").in(root.get("books")));
//
//            Query query = session.createQuery(criteriaUpdate);
//            query.executeUpdate();
//
//            session.getTransaction().commit();
//        }
    }

    @Override
    public void changeOrderStatus(Long orderId, OrderStatus status) {
        try {
            LOGGER.info("Changing order status with id=" + orderId);
            Order order = orderDao.getById(orderId);
            order.setStatus(status);
            if (status.equals(OrderStatus.CANCEL)) {
                cancelOrder(orderId);
            }
            //if order is done we set date and time of the end of the order
            else if (status.equals(OrderStatus.DONE)) {
                order.setDateOfDone(LocalDateTime.now());
                orderDao.update(order);
                LOGGER.info("Order with id=" + orderId + "has changed status to " + status);
            } else if (status.equals(OrderStatus.NEW)) {
                order.setDateOfDone(null);
                orderDao.update(order);
            }
        } catch (DaoException e) {
            LOGGER.warn("Method changeOrderStatus failed", e);
            throw new ServiceException("Method changeOrderStatus failed", e);
        }
    }

    @Override
    public double priceGetByPeriodOfTime(LocalDate fromDate, LocalDate tillDate) {
        try {
            Double price = orderDao.getPriceByPeriodOfTime(fromDate, tillDate);
            LOGGER.info("From '" + fromDate + "' till '" + tillDate + "' we earned: " + price);
            return price;
        } catch (DaoException e) {
            LOGGER.warn("Method priceGetByPeriodOfTime failed", e);
            throw new ServiceException("Method priceGetByPeriodOfTime failed", e);
        }
    }

    @Override
    public List<Order> ordersDoneByPeriodOfTime(LocalDate fromDate, LocalDate tillDate) {
        try {
            List<Order> list = orderDao.getOrdersDoneByPeriod(fromDate, tillDate);
            LOGGER.info("From '" + fromDate + "' till '" + tillDate + "' were done orders: \n" + list);
            return list;
        } catch (DaoException e) {
            LOGGER.warn("Method ordersDoneByPeriodOfTime failed", e);
            throw new ServiceException("Method ordersDoneByPeriodOfTime failed", e);
        }
    }

    @Override
    public void showDetails(Long orderId) {
        Order order =  orderDao.getById(orderId);
        LOGGER.info("\n" + order);
    }

    @Deprecated
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
                LOGGER.warn("No such type of sort");
                throw new ServiceException("Method sortOrdersBy failed");
        }
        return listToSort;
    }
}
