package com.action.orderAction;

import com.util.ConsoleScannerUtil;
import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.OrderStatus;
import com.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeOrderStatus implements IAction {

    private static final Logger LOGGER = LogManager.getLogger(ChangeOrderStatus.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            LOGGER.info("To change order status enter order ID, or enter '0' to back to previous menu");
            Long id = ConsoleScannerUtil.scanLong();
            if (id != 0) {
                OrderStatus statusTo = null;
                while (statusTo == null) {
                    LOGGER.info("Enter order status:\n" +
                            "'1' - to set as new, '2' - to set as cancel, '3' - to set as done)");
                    String status = ConsoleScannerUtil.scanString();
                    switch (status.trim()) {
                        case "1":
                            statusTo = OrderStatus.NEW;
                            break;
                        case "2":
                            statusTo = OrderStatus.CANCEL;
                            break;
                        case "3":
                            statusTo = OrderStatus.DONE;
                            break;
                        default:
                            LOGGER.info("There is no such status");
                            break;
                    }
                }
                facade.getOrderService().changeOrderStatus(id, statusTo);
                LOGGER.info("You changed status order " + facade.getOrderService().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
