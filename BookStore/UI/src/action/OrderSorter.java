package src.action;

import models.Order;
import src.Facade;
import util.comparators.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSorter implements IAction{

    Facade facade = Facade.getInstance();
    private Map<Integer, Comparator<Order>> sortOrdersBy;
    private List<Order> orders;
    private int id;

    public OrderSorter(int id, List<Order> orders){
        this.id = id;
        this.orders = orders;
        sortOrdersBy = new HashMap<>();
        sortOrdersBy.put(1, new OrderIdComparator());
        sortOrdersBy.put(2, new OrderStatusComparator());
        sortOrdersBy.put(3, new OrderPriceComparator());
        sortOrdersBy.put(4, new OrderDateOfDoneComparator());
    }

    @Override
    public void execute() {

        orders.sort(sortOrdersBy.get(id));
        System.out.println(orders);
    }
}
