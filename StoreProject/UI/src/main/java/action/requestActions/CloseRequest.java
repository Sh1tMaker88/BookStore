package action.requestActions;

import exceptions.DaoException;
import exceptions.ServiceException;
import facade.Facade;
import action.IAction;

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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            LOGGER.log(Level.INFO, "If you want to see the list of all requests enter '-1', if back to root menu enter '0'");
            System.out.println("To discard request enter request ID");
            Long id = Long.parseLong(reader.readLine());
            if (id.equals(-1L)) {
                System.out.println(facade.getRequestService().getRequestDao().getAll());
                LOGGER.log(Level.INFO, "Enter order ID");
                id = Long.parseLong(reader.readLine());
                facade.getRequestService().closeRequest(id);
                LOGGER.log(Level.INFO, "You closed request " + facade.getRequestService().getRequestDao().getById(id));
            } else if (id.equals(0L)){

            } else {
                facade.getRequestService().closeRequest(id);
                LOGGER.log(Level.INFO, "You closed request " + facade.getRequestService().getRequestDao().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Execute of execute failed", e);
            e.printStackTrace();
        }
    }
}