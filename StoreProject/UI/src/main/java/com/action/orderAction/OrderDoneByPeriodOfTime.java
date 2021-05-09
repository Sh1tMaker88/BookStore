package com.action.orderAction;

import com.action.IAction;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.propertyInjector.ApplicationContext;
import com.util.ConsoleScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class OrderDoneByPeriodOfTime implements IAction {
    private static final Logger LOGGER = LogManager.getLogger(PriceByPeriodOfTime.class.getName());
    final Facade facade = ApplicationContext.getInstance().getObject(Facade.class);

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
            facade.getOrderService().ordersDoneByPeriodOfTime(dateFrom, dateTill);
        } catch (DaoException | ServiceException e) {
            LOGGER.warn("Method execute failed", e);
        }
    }
}
