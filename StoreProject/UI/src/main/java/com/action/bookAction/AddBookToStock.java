package com.action.bookAction;

import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.ActionException;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AddBookToStock implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CreateBook.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
            LOGGER.info("To add book to stock enter book id, enter 0 to go to root menu");
            Long id = ConsoleScanner.scanLong();
            if (id != 0) {
                facade.getBookService().addBookToStock(id);
                LOGGER.info("You have added book to stock id=" + id);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        } catch (ActionException e) {
            LOGGER.warn("Execute of execute failed", e);
        }
    }
}
