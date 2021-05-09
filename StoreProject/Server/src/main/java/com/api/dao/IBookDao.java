package com.api.dao;

import com.model.Book;

import java.util.List;
import java.util.Set;


public interface IBookDao extends GenericDao<Book> {
    String getDescription(Long id);

    Set<Book> getBookThatHaveNoOrdersForPeriodOfTime(int monthToSetBookAsUnsold);
//    public void updateOrderCount(Book book);
}
