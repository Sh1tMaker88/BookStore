package src.action;

import models.Book;
import src.Facade;
import util.comparators.*;

import java.util.*;

public class BookSorter implements IAction{

    Facade facade = Facade.getInstance();
    private Map<Integer, Comparator<Book>> sortBooksBy;
    private List<Book> books;
    private int id;

    public BookSorter(int id, List<Book> books){
        this.id = id;
        this.books = books;
        sortBooksBy = new HashMap<>();
        sortBooksBy.put(1, new BookIdComparator());
        sortBooksBy.put(2, new BookNameComparator());
        sortBooksBy.put(3, new BookPriceComparator());
        sortBooksBy.put(4, new BookYearOfPublishComparator());
        sortBooksBy.put(5, new BookAvailabilityComparator());
    }

    @Override
    public void execute() {

        books.sort(sortBooksBy.get(id));
        System.out.println(books);
    }

}
