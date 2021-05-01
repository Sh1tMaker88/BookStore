package com.action.bookAction;

import com.exception.ActionException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.model.Book;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetBooksNotBoughtMoreThanSixMonth {

    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);
    private static final Logger LOGGER = LogManager.getLogger(GetBooksNotBoughtMoreThanSixMonth.class.getName());

    public List<Book> doIt() {
        try {
            return facade.getBookService().booksNotBoughtMoreThanSixMonth();
        } catch (ServiceException e) {
            LOGGER.warn("Method doIt from class GetBooksNotBoughtMoreThanSixMonth failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
