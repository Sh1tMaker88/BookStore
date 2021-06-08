package com.action.orderAction;

import com.exception.DaoException;
import com.facade.Facade;
import com.model.Order;
import com.exception.ActionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllOrders {

    private static final Logger LOGGER = LogManager.getLogger(GetAllOrders.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public List<Order> doIt() {
        try {
            return facade.getOrderService().getAllOrders();
        } catch (DaoException e){
            LOGGER.warn("Method doIt from class GetAllOrders failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
