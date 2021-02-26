package src.action.orderActions;

import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CancelOrder implements IAction {

    final Facade facade = Facade.getInstance();


    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("If you want to see the list of all orders enter '-1', if back to root menu enter '0'");
            System.out.println("To discard order enter order ID");
            int id = Integer.parseInt(reader.readLine());
            if (id == -1){
                System.out.println(facade.getOrderService().getOrderDao().getAll());
                System.out.println("Enter order ID");
                id = Integer.parseInt(reader.readLine());
                facade.getOrderService().cancelOrder(id);
                System.out.println("You discarded order " + facade.getOrderService().getOrderDao().getById(id));
            } else if (id == 0){

            } else {
                facade.getOrderService().cancelOrder(id);
                System.out.println("You discarded order " + facade.getOrderService().getOrderDao().getById(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
