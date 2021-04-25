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
import com.dao.util.Connector;
import com.facade.Facade;
import com.models.Book;
import com.models.BookStatus;
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
    @InjectByType
    private final Connector connector;

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
        this.connector = context.getObject(Connector.class);
        this.bookDao = context.getObject(BookDao.class);
        this.orderDao = context.getObject(OrderDao.class);
        this.requestDao = context.getObject(RequestDao.class);
        this.bookService = context.getObject(BookService.class);
        this.orderService = context.getObject(OrderService.class);
        this.requestService = context.getObject(RequestService.class);
        this.facade = context.getObject(Facade.class);


//        Book b1 = new Book("book_name", "author", "isbn", 444
//                , BookStatus.IN_STOCK, 10.5, 2021, "about life", LocalDate.now());
//        bookDao.create(b1);
//        Book b2 = bookDao.getById(15L);
//        b2.setName("changed");
//        b2.setAuthor("changed");
//        b2.setBookStatus(BookStatus.OUT_OF_STOCK);
//        b2.setPrice(55.1);
//        b2.setIsbn("changed");
//        b2.setPageNumber(1111);
//        b2.setYearOfPublish(2000);
//        b2.setOrderCount(5);
//        b2.setDescription("and death");
//        b2.setArrivalDate(LocalDate.parse("2020-02-20"));
//        bookDao.update(b2);
//        bookDao.delete(b2);

//        Book b1 = new Book();
//        Facade facade = ApplicationContext.getInstance().getObject(Facade.class);
//        facade.getBookService().addBookToStock(b1);
    }
}
