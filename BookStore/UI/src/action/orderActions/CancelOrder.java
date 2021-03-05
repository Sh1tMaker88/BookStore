package src.action.orderActions;

import exceptions.DaoException;
import exceptions.ServiceException;
import src.Facade;
import src.action.IAction;
import src.exceptions.ActionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CancelOrder implements IAction {

    private static final Logger LOGGER = Logger.getLogger(CancelOrder.class.getName());
    final Facade facade = Facade.getInstance();


    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.log(Level.INFO, "If you want to see the list of all orders enter '-1', if back to root menu enter '0'");
            LOGGER.log(Level.INFO, "To discard order enter order ID");
            int id = Integer.parseInt(reader.readLine());
            if (id == -1){
                System.out.println(facade.getOrderService().getOrderDao().getAll());
                LOGGER.log(Level.INFO, "Enter order ID");
                id = Integer.parseInt(reader.readLine());
                facade.getOrderService().cancelOrder(id);
                LOGGER.log(Level.INFO, "You discarded order " + facade.getOrderService().getOrderDao().getById(id));
            } else if (id == 0){

            } else {
                facade.getOrderService().cancelOrder(id);
                LOGGER.log(Level.INFO, "You discarded order " + facade.getOrderService().getOrderDao().getById(id));
            }
        } catch (DaoException | ServiceException e) {
            LOGGER.log(Level.WARNING, "Method execute failed", e);
            e.printStackTrace();
        }  catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
