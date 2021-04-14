package com.action.bookActions;

import com.exceptions.ActionException;
import com.exceptions.DaoException;
import com.models.Book;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllBooks {

    private static final Logger LOGGER = Logger.getLogger(GetAllBooks.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    public List<Book> doIt() {
        try {
            return facade.getBookService().getAllBooks();
        } catch (DaoException e){
            LOGGER.log(Level.WARNING, "Method doIt from class GetAllBooks failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
