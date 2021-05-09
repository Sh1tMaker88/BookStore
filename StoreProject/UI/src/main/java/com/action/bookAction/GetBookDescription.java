package com.action.bookAction;

import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.util.ConsoleScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetBookDescription implements IAction {
    private static final Logger LOGGER = LogManager.getLogger(GetBookDescription.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

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
