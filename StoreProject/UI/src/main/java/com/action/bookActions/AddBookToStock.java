package com.action.bookActions;

import com.action.IAction;
import com.exceptions.ActionException;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.models.Book;
import com.facade.Facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookToStock implements IAction {

    private static final Logger LOGGER = Logger.getLogger(AddBookToStock.class.getName());
    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            LOGGER.log(Level.INFO, "You can add book by 2 ways:\n- printing parameter 1 by 1\n" +
                    "- give all parameters(book name, book author, 'int' yearOfPublish, " +
                    "'double' price, 'String' isbn, pageNumber) separated by ','");
            String line = reader.readLine();
            String[] params = line.split(",");
            Book bookToAdd;
            if (params.length == 6) {
                bookToAdd = facade.getBookService().addBookToStock(params[0].trim(), params[1].trim(),
                        Integer.parseInt(params[2].trim()), Double.parseDouble(params[3].trim()),
                        params[4].trim(), Integer.parseInt(params[5].trim()));
            } else {
                LOGGER.log(Level.INFO, "Enter book author");
                String author = reader.readLine();
                LOGGER.log(Level.INFO, "Enter year of publish");
                int yearOfPublish = Integer.parseInt(reader.readLine());
                LOGGER.log(Level.INFO, "Enter price");
                double price = Double.parseDouble(reader.readLine());
                LOGGER.log(Level.INFO, "Enter isbn");
                String isbn = reader.readLine();
                LOGGER.log(Level.INFO, "Enter number of pages");
                int pages = Integer.parseInt(reader.readLine());
                bookToAdd = facade.getBookService().addBookToStock(line, author, yearOfPublish, price, isbn, pages);
            }
            LOGGER.log(Level.INFO, "You have added book:\n" + bookToAdd);
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (ActionException | IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}
