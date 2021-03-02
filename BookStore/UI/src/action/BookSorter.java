package src.action;

import models.Book;
import src.Facade;
import src.action.bookActions.GetAllBooks;
import src.action.bookActions.GetBooksNotBoughtMoreThanSixMonth;
import util.comparators.*;

import java.util.*;

public class BookSorter implements IAction{

    private Map<Integer, Comparator<Book>> sortBooksBy;
    private List<Book> books = new ArrayList<>();
    private int id;
    String method;

    public BookSorter(int id, String method){
        this.id = id;
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
        switch (method){
            case "getAll":
                setBooks(new GetAllBooks().doIt());
                break;
            case "oldBooks":
                setBooks(new GetBooksNotBoughtMoreThanSixMonth().doIt());
                break;
        }
        books.sort(sortBooksBy.get(id));
        System.out.println(books);
    }

}
