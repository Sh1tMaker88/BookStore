package com.api.dao;

import com.model.Book;
import com.model.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IOrderDao extends GenericDao<Order> {
    Set<Book> getBooksThatAreNotBought(int monthToSetBookAsUnsold);

    Double getPriceByPeriodOfTime(LocalDate fromDate, LocalDate tillDate);

    List<Order> getOrdersDoneByPeriod(LocalDate fromDate, LocalDate tillDate);
//    public Order createOrder(Order entity);
}
