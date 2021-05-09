package com.api.dao;

import com.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface IBookDao extends GenericDao<Book> {
    String getDescription(Long id);

    Set<Book> getBookThatHaveNoOrdersForPeriodOfTime(int monthToSetBookAsUnsold);
}
