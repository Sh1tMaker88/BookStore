package action.bookActions;

import exceptions.ServiceException;
import facade.Facade;
import models.Book;
import exceptions.ActionException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetBooksNotBoughtMoreThanSixMonth {

    final Facade facade = Facade.getInstance();

    private static final Logger LOGGER = Logger.getLogger(GetBooksNotBoughtMoreThanSixMonth.class.getName());
    public List<Book> doIt() {
        try {
            return facade.getBookService().booksNotBoughtMoreThanSixMonth();
        } catch (ServiceException e) {
            LOGGER.log(Level.WARNING, "Method doIt from class GetBooksNotBoughtMoreThanSixMonth failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
