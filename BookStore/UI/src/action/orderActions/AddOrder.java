package src.action.orderActions;

import exceptions.DaoException;
import exceptions.ServiceException;
import models.Book;
import service.OrderService;
import src.Facade;
import src.action.IAction;
import src.exceptions.ActionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddOrder implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddOrder.class.getName());

    @Override
    public void execute() {

        final Facade facade = Facade.getInstance();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.log(Level.INFO, "Enter customer name");
            String customerName = reader.readLine();
            boolean flag = true;
            List<Book> list = new ArrayList<>();
            System.out.println(facade.getBookService().getBookDao().getAll());
            //add some books to list
            while (flag) {
                LOGGER.log(Level.INFO, "Enter book ID");
                int id = Integer.parseInt(reader.readLine());
                //if there is this ID add book to list
                boolean isAnyID = facade.getBookService().getBookDao().getAll().stream().anyMatch(e -> e.getId() == id);
                if (isAnyID) {
                    Book book = facade.getBookService().getBookDao().getById(id);
                    list.add(book);
                } else {
                    LOGGER.log(Level.INFO, "There is no such ID");
                }
                LOGGER.log(Level.INFO, "If you want add more books enter 1, if no - 0");
                int continueToAdd = Integer.parseInt(reader.readLine());
                if (continueToAdd == 0) {
                    flag = false;
                }
            }
            System.out.println(facade.getOrderService().addOrder(customerName, list));
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
