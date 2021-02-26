package src.action.bookActions;

import models.Book;
import src.Facade;

import java.util.ArrayList;
import java.util.List;

public class GetBooksNotBoughtMoreThanSixMonth {
    final Facade facade = Facade.getInstance();
    List<Book> books = new ArrayList<>();

    public List<Book> doIt() {
//        try {
        books = facade.getBookService().getBookDao().getAll();
        books = facade.getBookService().booksNotBoughtMoreThanSixMonth();
        return books;
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
