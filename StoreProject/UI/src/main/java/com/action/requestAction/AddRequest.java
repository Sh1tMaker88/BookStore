package com.action.requestAction;

import com.util.ConsoleScannerUtil;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddRequest implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(AddRequest.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {

        try {
            LOGGER.info("To add request you must enter book ID, or enter '0' to back to previous menu");
            Long id = ConsoleScannerUtil.scanLong();
            if (id != 0) {
                facade.getRequestService().addRequest(id);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
