package src.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddOrder extends AbstractAction implements IAction{

    @Override
    public void execute() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter parameter 1");
            String param1 = reader.readLine();
            System.out.println("Enter parameter 2");
            int param2 = reader.read();
//            Object order = fasade.addOrder(param1, param2);
//            System.out.println(order);
        } catch (IOException e){
            System.err.println(e.getLocalizedMessage());
        }

    }
}
