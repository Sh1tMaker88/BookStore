package src.action;

import models.Order;
import src.Facade;
import src.action.orderActions.GetAllOrders;
import util.comparators.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSorter implements IAction{

    private Map<Integer, Comparator<Order>> sortOrdersBy;
    private List<Order> orders;
    private int id;
    String method;

    public OrderSorter(int id, String method){
        this.id = id;
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
        switch (method) {
            case "getAll":
                setOrders(new GetAllOrders().doIt());
                break;
        }
        setOrders(new GetAllOrders().doIt());
        orders.sort(sortOrdersBy.get(id));
        System.out.println(orders);
    }
}
