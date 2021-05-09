package com.action.requestAction;

import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AddRequest implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(AddRequest.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
            LOGGER.info("To add request you must enter book ID, or enter '0' to back to previous menu");
            Long id = ConsoleScanner.scanLong();
            if (id != 0) {
                facade.getRequestService().addRequest(id);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
