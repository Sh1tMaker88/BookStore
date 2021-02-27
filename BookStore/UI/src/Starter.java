package src;

import models.Book;
import models.Order;
import src.menu.MenuController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws IOException {

        Facade facade = Facade.getInstance();
        Book book1 = facade.getBookService().addBookToStock
                ("King", "Arthur", 2001,43.2, "2342345", 522);
        Book book2 = facade.getBookService().addBookToStock
                ("Gilead", "Stiven", 1963,35.5,"423asdf45", 443);
        Book book3 = facade.getBookService().addBookToStock
                ("Harry Potter", "Joanne Rowling", 1996,48.0,"42s3dsaf45", 840);
        Book book4 = facade.getBookService().addBookToStock
                ("Code Complete", "McConnell", 2004,52.5," 0-7356-1967-0", 869);
        List<Book> orderedBooks1 = new ArrayList<>();
        orderedBooks1.add(book2);
        orderedBooks1.add(book3);
        List<Book> orderedBooks2 = new ArrayList<>();
        orderedBooks2.add(book1);
        orderedBooks2.add(book3);
        Order order1 = facade.getOrderService().addOrder("Vadim", orderedBooks1);
        Order order2 = facade.getOrderService().addOrder("Helen", orderedBooks2);

        System.out.println(facade.getOrderService().getOrderDao().getAll());
        System.out.println("------------");

        LocalDate lc = LocalDate.parse("2018-08-30");
        LocalDate lc2 = LocalDate.now();
        book1.setArrivalDate(lc);
        book2.setArrivalDate(lc);
        book3.setArrivalDate(lc2);
        book4.setArrivalDate(lc2);

        MenuController.getInstance().run();
    }
}
