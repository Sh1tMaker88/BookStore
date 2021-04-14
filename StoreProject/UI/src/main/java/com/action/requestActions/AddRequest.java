package com.action.requestActions;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.action.bookActions.AddBookToStock;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.facade.Facade;
import com.models.Book;
import com.propertyInjector.ApplicationContext;

import java.io.IOException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddRequest implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddRequest.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {

        try {
            LOGGER.log(Level.INFO, "To add request you must enter book ID or enter a new book\n" +
                    "1 - to enter book ID\n2 - to enter new book");
            Long num = ConsoleScanner.scanLong();
            if (num.equals(1L)) {
                LOGGER.log(Level.INFO, "If you want see list of all books enter 0, or enter book ID");
                num = ConsoleScanner.scanLong();
                if (num.equals(0L)){
                    System.out.println(facade.getBookService().getAllBooks());
                    LOGGER.log(Level.INFO, "Enter book ID to add request");
                    num = ConsoleScanner.scanLong();
                }
                Book book = facade.getBookService().getById(num);
                facade.getRequestService().addRequest(book);
            } else if (num.equals(2L)){
                new AddBookToStock().execute();
                Book lastAddedBook = facade.getBookService().getAllBooks()
                        .stream()
                        .max(Comparator.comparingLong(Book::getId))
                        .get();
                Book book = facade.getBookService().getById(lastAddedBook.getId());
                facade.getBookService().discardBook(book.getId());
                facade.getRequestService().addRequest(book);
            } else {
                LOGGER.log(Level.INFO, "Incorrect input");
            }
        }  catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
