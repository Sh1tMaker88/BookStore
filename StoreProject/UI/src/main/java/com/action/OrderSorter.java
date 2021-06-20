package com.action;

import com.action.orderAction.GetAllOrders;
import com.action.util.SpringContextHolder;
import com.exception.ActionException;
import com.model.Order;
import com.util.comparator.OrderDateOfDoneComparator;
import com.util.comparator.OrderIdComparator;
import com.util.comparator.OrderPriceComparator;
import com.util.comparator.OrderStatusComparator;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
    private final ApplicationContext context;

    public OrderSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortOrdersBy = new HashMap<>();
        sortOrdersBy.put(1, new OrderIdComparator());
        sortOrdersBy.put(2, new OrderStatusComparator());
        sortOrdersBy.put(3, new OrderPriceComparator());
        sortOrdersBy.put(4, new OrderDateOfDoneComparator());
        this.context = SpringContextHolder.getApplicationContext();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setOrders(context.getBean(GetAllOrders.class).doIt());
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
