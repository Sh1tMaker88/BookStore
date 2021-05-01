package com.action.requestAction;

import com.exception.ActionException;
import com.exception.DaoException;
import com.facade.Facade;
import com.model.Request;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllRequests {

    private static final Logger LOGGER = LogManager.getLogger(GetAllRequests.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);


    public List<Request> doIt() {
        try {
            return facade.getRequestService().getAllRequests();
        } catch (DaoException e){
            LOGGER.warn("Method doIt from class GetAllRequests failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
