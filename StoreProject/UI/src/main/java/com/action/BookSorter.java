package com.action;

import com.action.bookAction.GetAllBooks;
import com.action.util.SpringContextHolder;
import com.exception.ActionException;
import com.model.Book;
import com.action.bookAction.GetBooksNotBoughtMoreThanSixMonth;
import com.util.comparator.*;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookSorter implements IAction{

    private static final Logger LOGGER = LogManager.getLogger(BookSorter.class.getName());
    private Map<Integer, Comparator<Book>> sortBooksBy;
    private List<Book> books;
    private int sortId;
    String method;
    private final ApplicationContext context;

    public BookSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortBooksBy = new HashMap<>();
        sortBooksBy.put(1, new BookIdComparator());
        sortBooksBy.put(2, new BookNameComparator());
        sortBooksBy.put(3, new BookPriceComparator());
        sortBooksBy.put(4, new BookYearOfPublishComparator());
        sortBooksBy.put(5, new BookAvailabilityComparator());
        this.context = SpringContextHolder.getApplicationContext();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setBooks(context.getBean(GetAllBooks.class).doIt());
                    break;
                case "oldBooks":
                    setBooks(context.getBean(GetBooksNotBoughtMoreThanSixMonth.class).doIt());
                    break;
            }
            books.sort(sortBooksBy.get(sortId));
            LOGGER.info(books.toString());
        } catch (ActionException e){
            LOGGER.warn("Method execute failed", e);
        }
    }
}
