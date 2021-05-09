package com.dao;

import com.api.dao.IBookDao;
import com.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BookDao extends AbstractDao<Book> implements IBookDao {

    private static final Logger LOGGER = LogManager.getLogger(BookDao.class.getName());

    public BookDao() {
    }

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
        return getAll().stream()
                .findFirst()
                .filter(e -> e.getId().equals(id))
                .get()
                .getDescription();
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
