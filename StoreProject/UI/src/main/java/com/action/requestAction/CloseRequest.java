package com.action.requestAction;

import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CloseRequest implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CloseRequest.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

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
