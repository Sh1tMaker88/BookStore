package com.action.orderActions;

import com.action.IAction;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CancelOrder implements IAction {

    private static final Logger LOGGER = Logger.getLogger(CancelOrder.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            LOGGER.log(Level.INFO, "If you want to see the list of all orders enter '-1', " +
                    "if back to root menu enter '0'");
            LOGGER.log(Level.INFO, "To discard order enter order ID");
            Long id = Long.parseLong(reader.readLine());
            if (id.equals(-1L)){
                System.out.println(facade.getOrderService().getOrderDao().getAll());
                LOGGER.log(Level.INFO, "Enter order ID");
                id = Long.parseLong(reader.readLine());
                facade.getOrderService().cancelOrder(id);
                LOGGER.log(Level.INFO, "You discarded order " + facade.getOrderService().getOrderDao().getById(id));
            } else if (id.equals(0L)){

            } else {
                facade.getOrderService().cancelOrder(id);
                LOGGER.log(Level.INFO, "You discarded order " + facade.getOrderService().getOrderDao().getById(id));
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
