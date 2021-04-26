package com.action.orderAction;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.model.Book;
import com.propertyInjector.ApplicationContext;

//todo delete all com.models import from actions

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddOrder implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddOrder.class.getName());

    @Override
    public void execute() {

        final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

        try  {
            LOGGER.log(Level.INFO, "Enter customer name");
            String customerName = ConsoleScanner.scanString();
            boolean flag = true;
            List<Book> list = new ArrayList<>();
            System.out.println(facade.getBookService().getAllBooks());
            //add some books to list
            while (flag) {
                LOGGER.log(Level.INFO, "Enter book ID");
                Long id = ConsoleScanner.scanLong();
                //if there is this ID add book to list
                boolean isAnyID = facade.getBookService().getAllBooks().stream().
                        anyMatch(e -> e.getId().equals(id));
                if (isAnyID) {
                    Book book = facade.getBookService().getById(id);
                    list.add(book);
                } else {
                    LOGGER.log(Level.INFO, "There is no such ID");
                }
                LOGGER.log(Level.INFO, "If you want add more books enter 1, if no - 0");
                int continueToAdd = ConsoleScanner.scanInt();
                if (continueToAdd == 0) {
                    flag = false;
                }
            }
            facade.getOrderService().addOrder(customerName, list);
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
