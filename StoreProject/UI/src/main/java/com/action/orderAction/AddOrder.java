package com.action.orderAction;

import com.model.Book;
import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddOrder implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(AddOrder.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try  {
            LOGGER.info("Enter customer name");
            String customerName = ConsoleScanner.scanString();
            boolean flag = true;
            List<Book> list = new ArrayList<>();
            while (flag) {
                LOGGER.info("Enter book ID. 0 - will get you back to root menu");
                Long id = ConsoleScanner.scanLong();
                if (id == 0L) {
                    flag = false;
                }
                //if there is this ID add book to list
                boolean isAnyID = facade.getBookService().getAllBooks().stream().
                        anyMatch(e -> e.getId().equals(id));
                if (isAnyID) {
                    list.add(facade.getBookService().getById(id));
                } else {
                    LOGGER.info("There is no such ID");
                }
                LOGGER.info("If you want add more book enter it ID, if no - 0");
                LOGGER.info("Already added: " + list.toString());
            }
            if (!list.isEmpty()) {
                facade.getOrderService().addOrder(customerName, list);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
