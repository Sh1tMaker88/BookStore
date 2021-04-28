package com.action.orderAction;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddOrder implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddOrder.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try  {
            LOGGER.log(Level.INFO, "Enter customer name");
            String customerName = ConsoleScanner.scanString();
            boolean flag = true;
            List<Long> list = new ArrayList<>();
            while (flag) {
                LOGGER.log(Level.INFO, "Enter book ID. 0 - will get you back to root menu");
                Long id = ConsoleScanner.scanLong();
                if (id == 0L) {
                    flag = false;
                }
                //if there is this ID add book to list
                boolean isAnyID = facade.getBookService().getAllBooks().stream().
                        anyMatch(e -> e.getId().equals(id));
                if (isAnyID) {
                    list.add(id);
                } else {
                    LOGGER.log(Level.INFO, "There is no such ID");
                }
                LOGGER.log(Level.INFO, "If you want add more book enter it ID, if no - 0");
                LOGGER.log(Level.INFO, "Already added: " + list.toString());
            }
            if (!list.isEmpty()) {
                facade.getOrderService().addOrder(customerName, list);
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
