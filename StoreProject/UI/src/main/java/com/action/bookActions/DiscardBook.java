package com.action.bookActions;

import com.action.ConsoleScanner;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.facade.Facade;
import com.action.IAction;
import com.exceptions.ActionException;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscardBook implements IAction {

    private static final Logger LOGGER = Logger.getLogger(DiscardBook.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try  {
            LOGGER.log(Level.INFO, "If you want to see the list of all books enter '-1', " +
                    "if back to root menu enter '0'");
            LOGGER.log(Level.INFO, "To discard book enter book ID");
            Long id = ConsoleScanner.scanLong();
            if (id.equals(-1L)){
                System.out.println(facade.getBookService().getAllBooks());
                LOGGER.log(Level.INFO, "Enter book ID");
                id = ConsoleScanner.scanLong();
                facade.getBookService().discardBook(id);
                LOGGER.log(Level.INFO, "You discarded book " + facade.getBookService().getById(id));
            } else if (id.equals(0L)){

            } else {
                facade.getBookService().discardBook(id);
                LOGGER.log(Level.INFO, "You discarded book " + facade.getBookService().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        }  catch (ActionException | IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
