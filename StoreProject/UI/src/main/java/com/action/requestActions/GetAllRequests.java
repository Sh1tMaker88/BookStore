package com.action.requestActions;

import com.exceptions.ActionException;
import com.exceptions.DaoException;
import com.facade.Facade;
import com.models.Request;
import com.propertyInjector.ApplicationContext;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllRequests {

    private static final Logger LOGGER = Logger.getLogger(GetAllRequests.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);


    public List<Request> doIt() {
        try {
            return facade.getRequestService().getAllRequests();
        } catch (DaoException e){
            LOGGER.log(Level.WARNING, "Method doIt from class GetAllRequests failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
