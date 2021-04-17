package com.action.bookActions;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exceptions.ActionException;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.models.Book;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookToStock implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddBookToStock.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.log(Level.INFO, "You can add book by 2 ways:\n- printing parameter 1 by 1\n" +
                    "- give all parameters(book name, book author, 'int' yearOfPublish, " +
                    "'double' price, 'String' isbn, pageNumber) separated by ','");
            String line = ConsoleScanner.scanString();
            String[] params = line.split(",");
            Book bookToAdd;
            if (params.length == 6) {
                bookToAdd = facade.getBookService().addBookToStock(params[0].trim(), params[1].trim(),
                        Integer.parseInt(params[2].trim()), Double.parseDouble(params[3].trim()),
                        params[4].trim(), Integer.parseInt(params[5].trim()));
            } else {
                LOGGER.log(Level.INFO, "Enter book author");
                String author = ConsoleScanner.scanString();
                LOGGER.log(Level.INFO, "Enter year of publish");
                int yearOfPublish = ConsoleScanner.scanInt();
                LOGGER.log(Level.INFO, "Enter price");
                double price = ConsoleScanner.scanDouble();
                LOGGER.log(Level.INFO, "Enter isbn");
                String isbn = ConsoleScanner.scanString();
                LOGGER.log(Level.INFO, "Enter number of pages");
                int pages = ConsoleScanner.scanInt();
                bookToAdd = facade.getBookService().addBookToStock(line, author, yearOfPublish, price, isbn, pages);
            }
            LOGGER.log(Level.INFO, "You have added book:\n" + bookToAdd);
//            reader.close();
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (ActionException | IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
