package src.action.bookActions;

import models.Book;
import src.Facade;

import java.util.List;

public class GetBooksNotBoughtMoreThanSixMonth {

    final Facade facade = Facade.getInstance();

    public List<Book> doIt() {
        return facade.getBookService().booksNotBoughtMoreThanSixMonth();
    }
}
