package src.action.bookActions;

import models.Book;
import src.Facade;

import java.util.List;

public class GetAllBooks {

    final Facade facade = Facade.getInstance();

    public List<Book> doIt() {
        return facade.getBookService().getBookDao().getAll();
    }
}
