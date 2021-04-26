package com.action.bookAction;

import com.exception.ActionException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.model.Book;
import com.propertyInjector.ApplicationContext;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetBooksNotBoughtMoreThanSixMonth {

    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

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
