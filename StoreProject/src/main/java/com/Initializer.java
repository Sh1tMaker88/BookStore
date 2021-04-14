package com;

import com.annotations.InjectByType;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.dao.IRequestDao;
import com.api.service.IBookService;
import com.api.service.IOrderService;
import com.api.service.IRequestService;
import com.dao.BookDao;
import com.dao.OrderDao;
import com.dao.RequestDao;
import com.facade.Facade;
import com.models.Book;
import com.models.Order;
import com.propertyInjector.ApplicationContext;
import com.propertyInjector.Runner;
import com.serialization.Deserializator;
import com.service.BookService;
import com.service.OrderService;
import com.service.RequestService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;


public class Initializer {

    @InjectByType
    private final IBookDao bookDao;
    @InjectByType
    private final IOrderDao orderDao;
    @InjectByType
    private final IRequestDao requestDao;
    @InjectByType
    private final IOrderService orderService;
    @InjectByType
    private final IBookService bookService;
    @InjectByType
    private final IRequestService requestService;
    @InjectByType
    private final Facade facade;

    static {
        try (InputStream configuration = new FileInputStream("UI/src/main/resources/logger.properties")) {
//            LogManager.getLogManager().readConfiguration(
//                    com.Starter.class.getResourceAsStream("/logging.properties"));
//            System.setProperty("java.util.logging.SimpleFormatter.format",
//                    "[%1$tF %1$tT] -%4$s- (%2$s) \"%5$s%6$s\"%n");

            LogManager.getLogManager().readConfiguration(configuration);
        } catch (IOException e) {
            System.err.println("There is no file configuration" + e.toString());
        }
    }

    public Initializer() {
        ApplicationContext context = Runner.run("com",
                new HashMap<>(Map.of(
//                        IBookDao.class, BookDao.class,
//                        IOrderDao.class, OrderDao.class,
//                        IRequestDao.class, RequestDao.class,
//                        IOrderService.class, OrderService.class,
//                        IBookService.class, BookService.class,
//                        IRequestService.class, RequestService.class
                )));
        this.bookDao = context.getObject(BookDao.class);
        this.orderDao = context.getObject(OrderDao.class);
        this.requestDao = context.getObject(RequestDao.class);
        this.bookService = context.getObject(BookService.class);
        this.orderService = context.getObject(OrderService.class);
        this.requestService = context.getObject(RequestService.class);
        this.facade = context.getObject(Facade.class);

        context.getObject(Deserializator.class);

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

//        System.out.println(facade.getBookService().getBookDao().getAll());
    }
}
