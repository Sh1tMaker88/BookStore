package com.action.orderAction;

import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.util.ConsoleScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PriceByPeriodOfTime implements IAction {
    private static final Logger LOGGER = LogManager.getLogger(PriceByPeriodOfTime.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            LOGGER.info("Enter from date (yyyy-MM-dd)");
            String from = ConsoleScanner.scanString();
            LocalDate dateFrom = LocalDate.parse(from);
            LOGGER.info("Enter till date (yyyy-MM-dd), if you want to choose today date enter 'now'");
            String till = ConsoleScanner.scanString();
            LocalDate dateTill = till.equalsIgnoreCase("now")
                    ? LocalDate.now()
                    : LocalDate.parse(till);
            facade.getOrderService().priceGetByPeriodOfTime(dateFrom, dateTill);
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
