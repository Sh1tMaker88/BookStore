package src;

import models.Book;
import models.Order;
import serialization.Deserializator;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;

public class Initializer {

    static int monthToSetBookAsUnsold;

    static {
        try (InputStream configuration = new FileInputStream("resources/logger.properties")) {
//            LogManager.getLogManager().readConfiguration(
//                    Starter.class.getResourceAsStream("/logging.properties"));
            LogManager.getLogManager().readConfiguration(configuration);
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "[%1$tF %1$tT] -%4$s- (%2$s) \"%5$s%6$s\"%n");
            Properties properties = new Properties();
            properties.load(configuration);
            monthToSetBookAsUnsold = Integer.valueOf(properties.getProperty("UNSOLD_BOOK"));
        } catch (IOException e) {
            System.err.println("There is no file configuration" + e.toString());
        }
    }

    public Initializer() {

//        Facade facade = Facade.getInstance();

//        Book book1 = facade.getBookService().addBookToStock
//                ("King", "Arthur", 2001, 43.2, "2342345", 522);
//        Book book2 = facade.getBookService().addBookToStock
//                ("Gilead", "Stiven", 1963, 35.5, "423asdf45", 443);
//        Book book3 = facade.getBookService().addBookToStock
//                ("Harry Potter", "Joanne Rowling", 1996, 48.0, "42s3dsaf45", 840);
//        Book book4 = facade.getBookService().addBookToStock
//                ("Code Complete", "McConnell", 2004, 52.5, " 0-7356-1967-0", 869);
//        List<Book> orderedBooks1 = new ArrayList<>();
//        orderedBooks1.add(book2);
//        orderedBooks1.add(book3);
//        List<Book> orderedBooks2 = new ArrayList<>();
//        orderedBooks2.add(book1);
//        orderedBooks2.add(book3);
//        Order order1 = facade.getOrderService().addOrder("Vadim", orderedBooks1);
//        Order order2 = facade.getOrderService().addOrder("Helen", orderedBooks2);
//
//        LocalDate lc = LocalDate.parse("2018-08-30");
//        LocalDate lc2 = LocalDate.now();
//        book1.setArrivalDate(lc);
//        book2.setArrivalDate(lc);
//        book3.setArrivalDate(lc2);
//        book4.setArrivalDate(lc2);
//
//        facade.getRequestService().addRequest(
//                new Book("Some book", "Crazy Author", 2021, 20.2, "adsfjnh32df", 333));
//        System.out.println(facade.getRequestService().getRequestDao().getAll());

    }
}
