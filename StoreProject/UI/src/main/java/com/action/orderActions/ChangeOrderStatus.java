package com.action.orderActions;

import com.action.IAction;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.models.OrderStatus;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeOrderStatus implements IAction {

    private static final Logger LOGGER = Logger.getLogger(ChangeOrderStatus.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);


    @Override
    public void execute() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            LOGGER.log(Level.INFO, "If you want to see the list of all orders enter '-1', if back to root menu enter '0'");
            Long id = Long.parseLong(reader.readLine());
            if (id.equals(-1L)) {
                System.out.println(facade.getOrderService().getOrderDao().getAll());
                LOGGER.log(Level.INFO, "To change order status enter order ID");
                id = Long.parseLong(reader.readLine());
                LOGGER.log(Level.INFO, "Enter order status:\n" +
                        "'1' - to set as new, '2' - to set as cancel, '3' - to set as done)");
                String status = reader.readLine();
                OrderStatus statusTo;
                switch (status.trim().toLowerCase()) {
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
                        statusTo = facade.getOrderService().getOrderDao().getById(id).getStatus();
                        break;
                }
                facade.getOrderService().changeOrderStatus(id, statusTo);
                LOGGER.log(Level.INFO, "You changed status order " + facade.getOrderService().getOrderDao().getById(id));
            } else if (id.equals(0L)) {

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