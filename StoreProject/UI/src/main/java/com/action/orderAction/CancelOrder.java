package com.action.orderAction;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CancelOrder implements IAction {

    private static final Logger LOGGER = Logger.getLogger(CancelOrder.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.log(Level.INFO, "If you want to see the list of all orders enter '-1', " +
                    "if back to root menu enter '0'");
            LOGGER.log(Level.INFO, "To discard order enter order ID");
            Long id = ConsoleScanner.scanLong();
            if (id.equals(-1L)){
                System.out.println(facade.getOrderService().getAllOrders());
                LOGGER.log(Level.INFO, "Enter order ID");
                id = ConsoleScanner.scanLong();
                facade.getOrderService().cancelOrder(id);
                LOGGER.log(Level.INFO, "You discarded order " + facade.getOrderService().getById(id));
            } else if (id.equals(0L)){

            } else {
                facade.getOrderService().cancelOrder(id);
                LOGGER.log(Level.INFO, "You discarded order " + facade.getOrderService().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}