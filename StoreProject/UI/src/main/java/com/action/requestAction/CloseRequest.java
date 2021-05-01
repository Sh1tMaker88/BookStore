package com.action.requestAction;

import com.menu.MenuController;
import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CloseRequest implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CloseRequest.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.info("To close request enter request ID, or enter '0' to back to previous menu");
            Long id = ConsoleScanner.scanLong();
            if (id != 0) {
                facade.getRequestService().closeRequest(id);
                LOGGER.info("You closed request " + facade.getRequestService().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
