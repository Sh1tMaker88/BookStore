package com.action.orderActions;

import com.exceptions.DaoException;
import com.facade.Facade;
import com.models.Order;
import com.exceptions.ActionException;
import com.propertyInjector.ApplicationContext;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllOrders {

    private static final Logger LOGGER = Logger.getLogger(GetAllOrders.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

    public List<Order> doIt() {
        try {
            return facade.getOrderService().getOrderDao().getAll();
        } catch (DaoException e){
            LOGGER.log(Level.WARNING, "Method doIt from class GetAllOrders failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
