package com.action.bookAction;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exception.ActionException;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.model.BookStatus;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookToStock implements IAction {

    private static final Logger LOGGER = Logger.getLogger(CreateBook.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
            LOGGER.log(Level.INFO, "To add book to stock enter book id, enter 0 to go to root menu");
            Long id = ConsoleScanner.scanLong();
            if (id != 0) {
                facade.getBookService().addBookToStock(id);
                LOGGER.log(Level.INFO, "You have added book to stock id=" + id);
            } else {

            }
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (ActionException | IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
