package com.action;

import com.action.orderActions.GetAllOrders;
import com.exceptions.ActionException;
import com.models.Order;
import com.util.comparators.OrderDateOfDoneComparator;
import com.util.comparators.OrderIdComparator;
import com.util.comparators.OrderPriceComparator;
import com.util.comparators.OrderStatusComparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderSorter implements IAction{

    private static final Logger LOGGER = Logger.getLogger(OrderSorter.class.getName());
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
            setOrders(new GetAllOrders().doIt());
            orders.sort(sortOrdersBy.get(sortId));
            System.out.println(orders);
        }
        catch (ActionException e){
            LOGGER.log(Level.WARNING, "Method execute failed", e);
        }
    }
}
