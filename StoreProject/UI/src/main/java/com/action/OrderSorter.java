package com.action;

import com.action.orderAction.GetAllOrders;
import com.exception.ActionException;
import com.model.Order;
import com.util.comparator.OrderDateOfDoneComparator;
import com.util.comparator.OrderIdComparator;
import com.util.comparator.OrderPriceComparator;
import com.util.comparator.OrderStatusComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSorter implements IAction{

    private static final Logger LOGGER = LogManager.getLogger(OrderSorter.class.getName());
    private Map<Integer, Comparator<Order>> sortOrdersBy;
    private List<Order> orders;
    private int sortId;
    String method;

    public OrderSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortOrdersBy = new HashMap<>();
        sortOrdersBy.put(1, new OrderIdComparator());
        sortOrdersBy.put(2, new OrderStatusComparator());
        sortOrdersBy.put(3, new OrderPriceComparator());
        sortOrdersBy.put(4, new OrderDateOfDoneComparator());
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setOrders(new GetAllOrders().doIt());
                    break;
            }
            orders.sort(sortOrdersBy.get(sortId));
            LOGGER.info(orders.toString());
        }
        catch (ActionException e){
            LOGGER.warn("Method execute failed", e);
        }
    }
}
