package src.action.requestActions;

import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CloseRequest implements IAction {

    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("If you want to see the list of all requests enter '-1', if back to root menu enter '0'");
            System.out.println("To discard request enter request ID");
            int id = Integer.parseInt(reader.readLine());
            if (id == -1){
                System.out.println(facade.getRequestService().getRequestDao().getAll());
                System.out.println("Enter order ID");
                id = Integer.parseInt(reader.readLine());
                facade.getRequestService().closeRequest(id);
                System.out.println("You closed request " + facade.getRequestService().getRequestDao().getById(id));
            } else if (id == 0){

            } else {
                facade.getRequestService().closeRequest(id);
                System.out.println("You closed request " + facade.getRequestService().getRequestDao().getById(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
