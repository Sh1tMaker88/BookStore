package src.action.requestActions;

import exceptions.DaoException;
import src.Facade;
import src.action.IAction;
import src.exceptions.ActionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CloseRequest implements IAction {

    private static final Logger LOGGER = Logger.getLogger(CloseRequest.class.getName());
    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.log(Level.INFO, "If you want to see the list of all requests enter '-1', if back to root menu enter '0'");
            System.out.println("To discard request enter request ID");
            int id = Integer.parseInt(reader.readLine());
            if (id == -1){
                System.out.println(facade.getRequestService().getRequestDao().getAll());
                LOGGER.log(Level.INFO, "Enter order ID");
                id = Integer.parseInt(reader.readLine());
                facade.getRequestService().closeRequest(id);
                LOGGER.log(Level.INFO, "You closed request " + facade.getRequestService().getRequestDao().getById(id));
            } else if (id == 0){

            } else {
                facade.getRequestService().closeRequest(id);
                LOGGER.log(Level.INFO, "You closed request " + facade.getRequestService().getRequestDao().getById(id));
            }
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method cancelOrder failed", e);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getLocalizedMessage());
            throw new ActionException("Action CancelOrder-execute failed");
        }
    }
}
