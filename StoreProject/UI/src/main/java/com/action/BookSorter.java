package com.action;

import com.action.bookActions.GetAllBooks;
import com.exceptions.ActionException;
import com.models.Book;
import com.action.bookActions.GetBooksNotBoughtMoreThanSixMonth;
import com.util.comparators.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookSorter implements IAction{

    private static final Logger LOGGER = Logger.getLogger(BookSorter.class.getName());
    private Map<Integer, Comparator<Book>> sortBooksBy;
    private List<Book> books;
    private int sortId;
    String method;

    public BookSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortBooksBy = new HashMap<>();
        sortBooksBy.put(1, new BookIdComparator());
        sortBooksBy.put(2, new BookNameComparator());
        sortBooksBy.put(3, new BookPriceComparator());
        sortBooksBy.put(4, new BookYearOfPublishComparator());
        sortBooksBy.put(5, new BookAvailabilityComparator());
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setBooks(new GetAllBooks().doIt());
                    break;
                case "oldBooks":
                    setBooks(new GetBooksNotBoughtMoreThanSixMonth().doIt());
                    break;
            }
            books.sort(sortBooksBy.get(sortId));
            System.out.println(books);
        } catch (ActionException e){
            LOGGER.log(Level.WARNING, "Method execute failed", e);
        }
    }
}
