package com.action.orderAction;

import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import com.util.ConsoleScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class GetOrderDetails implements IAction {
    private static final Logger LOGGER = LogManager.getLogger(GetOrderDetails.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.info("Enter order ID to see its details or '0' to back to root menu");
            Long id = ConsoleScanner.scanLong();
            if (!id.equals(0L)){
                facade.getOrderService().showDetails(id);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
