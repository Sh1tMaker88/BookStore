package src.action.requestActions;

import models.Book;
import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddRequest implements IAction {

    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("To add request you must enter book ID or enter a new book\n" +
                    "1 - to enter book ID\n2 - to enter new book");
            int num = Integer.parseInt(reader.readLine());
            if (num == 1) {
                System.out.println("If you want see list of all books enter 0, or enter book ID");
                num = Integer.parseInt(reader.readLine());
                if (num == 0){
                    System.out.println(facade.getBookService().getBookDao().getAll());
                    System.out.println("Enter book ID to add request");
                }
                num = Integer.parseInt(reader.readLine());
                Book book = facade.getBookService().getBookDao().getById(num);
                facade.getRequestService().addRequest(book);
            } else if (num == 2){
                //todo use method and then find last added ID
                System.out.println("You can add book by 2 ways:\n- printing parameter 1 by 1\n" +
                        "- give all parameters(book name, book author, 'int' yearOfPublish, " +
                        "'double' price, 'String' isbn, pageNumber) separated by ','");
                String line = reader.readLine();
                String[] params = line.split(",");
                Book bookToAdd;
                if (params.length == 6){
                    bookToAdd = facade.getBookService().addBookToStock(params[0].trim(), params[1].trim(),
                            Integer.parseInt(params[2].trim()), Double.parseDouble(params[3].trim()),
                            params[4].trim(), Integer.parseInt(params[5].trim()));
                } else {
                    System.out.println("Enter book author");
                    String author = reader.readLine();
                    System.out.println("Enter year of publish");
                    int yearOfPublish = Integer.parseInt(reader.readLine());
                    System.out.println("Enter price");
                    double price = Double.parseDouble(reader.readLine());
                    System.out.println("Enter isbn");
                    String isbn = reader.readLine();
                    System.out.println("Enter number of pages");
                    int pages = Integer.parseInt(reader.readLine());
                    bookToAdd = facade.getBookService().addBookToStock(line, author, yearOfPublish, price, isbn, pages);
                }
                facade.getBookService().discardBook(bookToAdd.getId());
                facade.getRequestService().addRequest(bookToAdd);
            } else {
                System.out.println("Incorrect input");
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
