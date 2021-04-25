package com.action.bookActions;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exceptions.ActionException;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.facade.Facade;
import com.models.BookStatus;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookToStock implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddBookToStock.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
            LOGGER.log(Level.INFO, "You can add book by 2 ways:\n" +
                    "- give all parameters(book name, book author, 'String' isbn, " +
                    "number of pages, 'double' price, 'int' yearOfPublish\n, 'String' description, " +
                    "status('in_stock' or 'out_of_stock'), arrival date format 'yyyy-MM-dd') separated by ','\n" +
                    "- give parameters without arrival date and status(status become 'in_stock' and date set for today\n");
            String line = ConsoleScanner.scanString();
            String[] params = line.split(",");
            if (params.length == 9) {
                facade.getBookService().addBookToStock(params[0].trim(), params[1].trim(), params[2].trim()
                        , Integer.parseInt(params[3].trim()), Double.parseDouble(params[4].trim())
                        , Integer.parseInt(params[5].trim()), params[6].trim()
                        , BookStatus.valueOf(params[7].trim().toUpperCase()), LocalDate.parse(params[8]));
            } else {
                facade.getBookService().addBookToStock(params[0].trim(), params[1].trim(), params[2].trim()
                        , Integer.parseInt(params[3].trim()), Double.parseDouble(params[4].trim())
                        , Integer.parseInt(params[5].trim()), params[6].trim());
            }
            LOGGER.log(Level.INFO, "You have added book\n");
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (ActionException | IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
