package com.action.bookAction;

import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import com.util.ConsoleScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetBookDescription implements IAction {
    private static final Logger LOGGER = LogManager.getLogger(GetBookDescription.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.info("To see description of book enter book id. If you want to back to root menu enter '0'");
            Long id = ConsoleScanner.scanLong();
            facade.getBookService().showDescription(id);
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
