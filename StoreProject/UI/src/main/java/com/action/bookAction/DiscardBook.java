package com.action.bookAction;

import com.util.ConsoleScanner;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.action.IAction;
import com.exception.ActionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscardBook implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(DiscardBook.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

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
