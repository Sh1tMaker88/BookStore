package com.action.orderAction;

import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.util.ConsoleScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetOrderDetails implements IAction {
    private static final Logger LOGGER = LogManager.getLogger(GetOrderDetails.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public GetOrderDetails(){
    }

    @Override
    public void execute() {
        try {
            System.out.println(facade);
            LOGGER.info("Enter order ID to see its details or '0' to back to root menu");
            Long id = ConsoleScannerUtil.scanLong();
            if (!id.equals(0L)){
                facade.getOrderService().showDetails(id);
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
