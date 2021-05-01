package com.action.orderAction;

import com.exception.DaoException;
import com.facade.Facade;
import com.model.Order;
import com.exception.ActionException;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllOrders {

    private static final Logger LOGGER = LogManager.getLogger(GetAllOrders.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    public List<Order> doIt() {
        try {
            return facade.getOrderService().getAllOrders();
        } catch (DaoException e){
            LOGGER.warn("Method doIt from class GetAllOrders failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
