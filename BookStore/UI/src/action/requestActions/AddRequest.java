package src.action.requestActions;

import exceptions.DaoException;
import exceptions.ServiceException;
import models.Book;
import service.OrderService;
import src.Facade;
import src.action.IAction;
import src.action.bookActions.AddBookToStock;
import src.exceptions.ActionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddRequest implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddRequest.class.getName());
    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.log(Level.INFO, "To add request you must enter book ID or enter a new book\n" +
                    "1 - to enter book ID\n2 - to enter new book");
            int num = Integer.parseInt(reader.readLine());
            if (num == 1) {
                LOGGER.log(Level.INFO, "If you want see list of all books enter 0, or enter book ID");
                num = Integer.parseInt(reader.readLine());
                if (num == 0){
                    System.out.println(facade.getBookService().getBookDao().getAll());
                    LOGGER.log(Level.INFO, "Enter book ID to add request");
                }
                num = Integer.parseInt(reader.readLine());
                Book book = facade.getBookService().getBookDao().getById(num);
                facade.getRequestService().addRequest(book);
            } else if (num == 2){
                new AddBookToStock().execute();
                Book lastAddedBook = facade.getBookService().getBookDao().getAll()
                        .stream()
                        .max(Comparator.comparingInt(Book::getId))
                        .get();
                Book book = facade.getBookService().getBookDao().getById(lastAddedBook.getId());
                facade.getBookService().discardBook(book.getId());
                facade.getRequestService().addRequest(book);
            } else {
                LOGGER.log(Level.INFO, "Incorrect input");
            }
        }  catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
