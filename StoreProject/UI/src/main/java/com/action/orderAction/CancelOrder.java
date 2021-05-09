package com.action.orderAction;

import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelOrder implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CancelOrder.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

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
