package src.action.bookActions;

import models.Book;
import src.Facade;
import src.action.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddBookToStock implements IAction {

    final Facade facade = Facade.getInstance();

    @Override
    public void execute() {
        try {

            System.out.println("You can add book by 2 ways:\n- printing parameter 1 by 1\n" +
                    "- give all parameters(book name, book author, 'int' yearOfPublish, " +
                    "'double' price, 'String' isbn, pageNumber) separated by ','");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
            System.out.println("You have added book:\n" + bookToAdd);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
