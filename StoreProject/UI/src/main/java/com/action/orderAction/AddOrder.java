package com.action.orderAction;

import com.model.AIdentity;
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
import java.util.stream.Collectors;

public class AddOrder implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(AddOrder.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.info("Enter customer name");
            String customerName = ConsoleScanner.scanString();
            boolean flag = true;
            List<Book> list = new ArrayList<>();
            LOGGER.info("Enter book ID. 0 - will get you back to root menu");
            while (flag) {
                Long id = ConsoleScanner.scanLong();
                if (id == 0L) {
                    flag = false;
                } else {
                    list.add(facade.getBookService().getById(id));
                    LOGGER.info("Already added books with ID: " + list.stream().map(AIdentity::getId).collect(Collectors.toList()));
                    LOGGER.info("If you want add more book enter it ID, if no - '0' and it will finish your order");
                }
            }
            if (!list.isEmpty()) {
                facade.getOrderService().addOrder(customerName, list);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
