package src.action.orderActions;

import models.Book;
import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddOrder implements IAction {

    @Override
    public void execute() {
        final Facade facade = Facade.getInstance();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter customer name");
            String customerName = reader.readLine();
            boolean flag = true;
            List<Book> list = new ArrayList<>();
            System.out.println(facade.getBookService().getBookDao().getAll());
            //add some books to list
            while (flag) {
                System.out.println("Enter book ID");
                int id = Integer.parseInt(reader.readLine());
                //if there is this ID add book to list
                boolean isAnyID = facade.getBookService().getBookDao().getAll().stream().anyMatch(e -> e.getId() == id);
                if (isAnyID) {
                    Book book = facade.getBookService().getBookDao().getById(id);
                    list.add(book);
                } else {
                    System.out.println("There is no such ID");
                }
                System.out.println("If you want add more books enter 1, if no - 0");
                int continueToAdd = Integer.parseInt(reader.readLine());
                if (continueToAdd == 0) {
                    flag = false;
                }
            }
            System.out.println(facade.getOrderService().addOrder(customerName, list));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
