package com.dao;

import com.api.dao.IOrderDao;
import com.model.Book;
import com.model.Order;
import com.model.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDao.class.getName());

    @Override
    protected String getClassName() {
        return "Order";
    }

    @Override
    protected Class<Order> getClazz() {
        return Order.class;
    }

    //todo rework to selecting using Criteria
    @Override
    public Set<Book> getBooksThatAreNotBought(int monthToSetBookAsUnsold) {
        return getAll().stream()
                .filter(order -> !order.getOrderDate()
                        .plusMonths(monthToSetBookAsUnsold)
                        .isBefore(LocalDateTime.now()))
                .flatMap(el -> el.getBooks().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Double getPriceByPeriodOfTime(LocalDate fromDate, LocalDate tillDate) {
        return getAll().stream()
                .filter(e -> e.getStatus().equals(OrderStatus.DONE))
                .filter(e -> e.getDateOfDone().toLocalDate().isAfter(fromDate)
                        && e.getDateOfDone().toLocalDate().isBefore(tillDate))
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    @Override
    public List<Order> getOrdersDoneByPeriod(LocalDate fromDate, LocalDate tillDate) {
        return getAll().stream()
                .filter(e -> e.getStatus().equals(OrderStatus.DONE))
                .filter(e -> e.getDateOfDone().toLocalDate().isAfter(fromDate)
                        && e.getDateOfDone().toLocalDate().isBefore(tillDate))
                .collect(Collectors.toList());
    }
}
