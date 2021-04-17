package com.action.requestActions;

import com.action.ConsoleScanner;
import com.action.IAction;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CloseRequest implements IAction {

    private static final Logger LOGGER = Logger.getLogger(CloseRequest.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    @Override
    public void execute() {
        try {
            LOGGER.log(Level.INFO, "If you want to see the list of all requests enter '-1', if back to root menu enter '0'");
            System.out.println("To discard request enter request ID");
            Long id = ConsoleScanner.scanLong();
            if (id.equals(-1L)) {
                System.out.println(facade.getRequestService().getAllRequests());
                LOGGER.log(Level.INFO, "Enter order ID");
                id = ConsoleScanner.scanLong();
                facade.getRequestService().closeRequest(id);
                LOGGER.log(Level.INFO, "You closed request " + facade.getRequestService().getById(id));
            } else if (id.equals(0L)){

            } else {
                facade.getRequestService().closeRequest(id);
                LOGGER.log(Level.INFO, "You closed request " + facade.getRequestService().getById(id));
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
