package com.dao;

import com.api.dao.IBookDao;
import com.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BookDao extends AbstractDao<Book> implements IBookDao {

    @Override
    protected String getClassName() {
        return "Book";
    }

    @Override
    protected Class<Book> getClazz() {
        return Book.class;
    }

    @Override
    public String getDescription(Long id) {
        return getById(id).getDescription();
    }

    @Override
    public Set<Book> getBookThatHaveNoOrdersForPeriodOfTime(int monthToSetBookAsUnsold) {
        return getAll().stream()
                .filter(book -> book.getArrivalDate()
                        .plusMonths(monthToSetBookAsUnsold)
                        .isBefore(LocalDate.now()))
                .filter(el -> el.getOrders().isEmpty())
                .collect(Collectors.toSet());
    }
}
