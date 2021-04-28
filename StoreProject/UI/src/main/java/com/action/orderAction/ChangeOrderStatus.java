package com.action.orderAction;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.OrderStatus;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeOrderStatus implements IAction {

    private static final Logger LOGGER = Logger.getLogger(ChangeOrderStatus.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.log(Level.INFO, "To change order status enter order ID");
            Long id = ConsoleScanner.scanLong();

            OrderStatus statusTo = null;
            while (statusTo == null) {
                LOGGER.log(Level.INFO, "Enter order status:\n" +
                        "'1' - to set as new, '2' - to set as cancel, '3' - to set as done)");
                String status = ConsoleScanner.scanString();
                switch (status.trim()) {
                    case "1":
                        statusTo = OrderStatus.NEW;
                        break;
                    case "2":
                        statusTo = OrderStatus.CANCEL;
                        break;
                    case "3":
                        statusTo = OrderStatus.DONE;
                        break;
                    default:
                        LOGGER.log(Level.INFO, "There is no such status");
                        break;
                }
            }
            facade.getOrderService().changeOrderStatus(id, statusTo);
            LOGGER.log(Level.INFO, "You changed status order " + facade.getOrderService().getById(id));
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
