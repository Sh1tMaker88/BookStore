package action.requestActions;

import exceptions.DaoException;
import facade.Facade;
import models.Request;
import exceptions.ActionException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllRequests {

    private static final Logger LOGGER = Logger.getLogger(GetAllRequests.class.getName());
    final Facade facade = Facade.getInstance();


    public List<Request> doIt() {
        try {
            return facade.getRequestService().getRequestDao().getAll();
        } catch (DaoException e){
            LOGGER.log(Level.WARNING, "Method doIt from class GetAllRequests failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
