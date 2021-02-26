package src.action.bookActions;

import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiscardBook implements IAction {

    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("If you want to see the list of all books enter '-1', if back to root menu enter '0'");
            System.out.println("To discard book enter book ID");
            int id = Integer.parseInt(reader.readLine());
            if (id == -1){
                System.out.println(facade.getBookService().getBookDao().getAll());
                System.out.println("Enter book ID");
                id = Integer.parseInt(reader.readLine());
                facade.getBookService().discardBook(id);
                System.out.println("You discarded book " + facade.getBookService().getBookDao().getById(id));
            } else if (id == 0){

            } else {
                facade.getBookService().discardBook(id);
                System.out.println("You discarded book " + facade.getBookService().getBookDao().getById(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
