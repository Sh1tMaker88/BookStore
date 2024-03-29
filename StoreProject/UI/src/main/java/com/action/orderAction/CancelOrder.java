package com.action.orderAction;

import com.model.Order;
import com.util.ConsoleScannerUtil;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelOrder implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(CancelOrder.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            LOGGER.info("To discard order enter order ID, if you want back to root menu enter '0'");
            Long id = ConsoleScannerUtil.scanLong();
            if (!id.equals(0L)){
                Order order = facade.getOrderService().cancelOrder(id);
                LOGGER.info("You discarded order " + order.getId());
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
