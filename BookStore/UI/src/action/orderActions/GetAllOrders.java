package src.action.orderActions;

import models.Order;
import src.Facade;

import java.util.List;

public class GetAllOrders {

    final Facade facade = Facade.getInstance();

    public List<Order> doIt() {
//        try {
        return facade.getOrderService().getOrderDao().getAll();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
