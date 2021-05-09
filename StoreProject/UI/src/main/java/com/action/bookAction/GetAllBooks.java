package com.action.bookAction;

import com.exception.ActionException;
import com.exception.DaoException;
import com.model.Book;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllBooks {

    private static final Logger LOGGER = LogManager.getLogger(GetAllBooks.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    public List<Book> doIt() {
        try {
            return facade.getBookService().getAllBooks();
        } catch (DaoException e){
            LOGGER.warn("Method doIt from class GetAllBooks failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
