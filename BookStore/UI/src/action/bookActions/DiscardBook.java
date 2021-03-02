package src.action.bookActions;

import exceptions.DaoException;
import exceptions.ServiceException;
import src.Facade;
import src.action.IAction;
import src.exceptions.ActionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscardBook implements IAction {

    private static final Logger LOGGER = Logger.getLogger(DiscardBook.class.getName());
    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.log(Level.INFO, "If you want to see the list of all books enter '-1', if back to root menu enter '0'");
            LOGGER.log(Level.INFO, "To discard book enter book ID");
            int id = Integer.parseInt(reader.readLine());
            if (id == -1){
                System.out.println(facade.getBookService().getBookDao().getAll());
                LOGGER.log(Level.INFO, "Enter book ID");
                id = Integer.parseInt(reader.readLine());
                facade.getBookService().discardBook(id);
                LOGGER.log(Level.INFO, "You discarded book " + facade.getBookService().getBookDao().getById(id));
            } else if (id == 0){

            } else {
                facade.getBookService().discardBook(id);
                LOGGER.log(Level.INFO, "You discarded book " + facade.getBookService().getBookDao().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        }  catch (ActionException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
