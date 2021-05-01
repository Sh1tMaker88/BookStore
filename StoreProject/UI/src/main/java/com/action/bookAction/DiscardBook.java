package com.action.bookAction;

import com.util.ConsoleScanner;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.action.IAction;
import com.exception.ActionException;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DiscardBook implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(DiscardBook.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.info("To discard book enter book ID, or enter '0' to back to root menu");
            Long id = ConsoleScanner.scanLong();
            if (id != 0) {
                facade.getBookService().discardBook(id);
                LOGGER.info("You discarded book " + facade.getBookService().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        } catch (ActionException e) {
            LOGGER.warn("Execute of execute failed", e);
        }
    }
}
