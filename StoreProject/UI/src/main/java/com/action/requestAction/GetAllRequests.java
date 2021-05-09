package com.action.requestAction;

import com.exception.ActionException;
import com.exception.DaoException;
import com.facade.Facade;
import com.model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllRequests {

    private static final Logger LOGGER = LogManager.getLogger(GetAllRequests.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }


    public List<Request> doIt() {
        try {
            return facade.getRequestService().getAllRequests();
        } catch (DaoException e){
            LOGGER.warn("Method doIt from class GetAllRequests failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
