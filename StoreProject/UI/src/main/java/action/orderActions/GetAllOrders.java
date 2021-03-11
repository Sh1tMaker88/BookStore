package action.orderActions;

import exceptions.DaoException;
import facade.Facade;
import models.Order;
import exceptions.ActionException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllOrders {

    private static final Logger LOGGER = Logger.getLogger(GetAllOrders.class.getName());
    final Facade facade = Facade.getInstance();

    public List<Order> doIt() {
        try {
            return facade.getOrderService().getOrderDao().getAll();
        } catch (DaoException e){
            LOGGER.log(Level.WARNING, "Method doIt from class GetAllOrders failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
