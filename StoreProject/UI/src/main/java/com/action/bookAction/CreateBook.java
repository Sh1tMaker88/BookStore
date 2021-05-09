package com.action.bookAction;

import com.util.ConsoleScanner;
import com.action.IAction;
import com.exception.ActionException;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.model.BookStatus;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

public class CreateBook implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CreateBook.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
            LOGGER.info("You can create book by 2 ways:\n" +
                    "- give all parameters(book name, book author, 'String' isbn, " +
                    "number of pages, 'double' price, 'int' yearOfPublish\n, 'String' description, " +
                    "status('in_stock' or 'out_of_stock'), arrival date format 'yyyy-MM-dd') separated by ','\n" +
                    "- give parameters without arrival date and status(status become 'in_stock' and date set for today\n" +
                    "If you dont want to add book enter '0'");
            String line = ConsoleScanner.scanString();
            String[] params = line.split(",");
            if (!line.equals("0")) {
                if (params.length == 9) {
                    facade.getBookService().createBook(params[0].trim(), params[1].trim(), params[2].trim()
                            , Integer.parseInt(params[3].trim()), Double.parseDouble(params[4].trim())
                            , Integer.parseInt(params[5].trim()), params[6].trim()
                            , BookStatus.valueOf(params[7].trim().toUpperCase()), LocalDate.parse(params[8]));
                } else {
                    facade.getBookService().createBook(params[0].trim(), params[1].trim(), params[2].trim()
                            , Integer.parseInt(params[3].trim()), Double.parseDouble(params[4].trim())
                            , Integer.parseInt(params[5].trim()), params[6].trim());
                }
                LOGGER.info("You have added book\n");
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        } catch (ActionException e) {
            LOGGER.warn("Execute of execute failed", e);
        }
    }
}
