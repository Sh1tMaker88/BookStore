package com.action.orderAction;

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
public class CancelOrder implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CancelOrder.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            LOGGER.info("To discard order enter order ID, if you want back to root menu enter '0'");
            Long id = ConsoleScanner.scanLong();
            if (!id.equals(0L)){
                facade.getOrderService().cancelOrder(id);
                LOGGER.info("You discarded order " + facade.getOrderService().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
