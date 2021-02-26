package src.action.orderActions;

import models.OrderStatus;
import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeOrderStatus implements IAction {

    final Facade facade = Facade.getInstance();


    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("If you want to see the list of all orders enter '-1', if back to root menu enter '0'");

            int id = Integer.parseInt(reader.readLine());
            if (id == -1) {
                System.out.println(facade.getOrderService().getOrderDao().getAll());
                System.out.println("To change order status enter order ID");
                id = Integer.parseInt(reader.readLine());
                System.out.println("Enter order status:\n" +
                        "'1' - to set as new, '2' - to set as cancel, '3' - to set as done)");
                String status = reader.readLine();
                OrderStatus statusTo;
                switch (status.trim().toLowerCase()) {
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
                        System.out.println("There is no such status");
                        statusTo = facade.getOrderService().getOrderDao().getById(id).getStatus();
                        break;
                }
                facade.getOrderService().changeOrderStatus(id, statusTo);
                System.out.println("You changed status order " + facade.getOrderService().getOrderDao().getById(id));
            } else if (id == 0) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
