package com;

import com.action.orderAction.GetOrderDetails;
import com.facade.Facade;
import com.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Initializer {

//    @InjectByType
//    private final IBookDao bookDao;
//    @InjectByType
//    private final IOrderDao orderDao;
//    @InjectByType
//    private final IRequestDao requestDao;
//    @InjectByType
//    private final IOrderService orderService;
//    @InjectByType
//    private final IBookService bookService;
//    @InjectByType
//    private final IRequestService requestService;
//    @InjectByType
//    private final Facade facade;
//    @InjectByType
//    private final Connector connector;

    static {

    }

    public Initializer() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("appContext.xml");
//        BookService bookDao = context.getBean(BookService.class);
//        System.out.println(bookDao);
//        Facade facade = context.getBean(Facade.class);
//        System.out.println(facade);
//        GetOrderDetails getOrderDetails = context.getBean(GetOrderDetails.class);
//        System.out.println(getOrderDetails);

//        MyApplicationContext myContext = Runner.run("com",
//                new HashMap<>(Map.of(
//                        IBookDao.class, BookDao.class,
//                        IOrderDao.class, OrderDao.class,
//                        IRequestDao.class, RequestDao.class,
//                        IOrderService.class, OrderService.class,
//                        IBookService.class, BookService.class,
//                        IRequestService.class, RequestService.class
//                )));
//        this.connector = myContext.getObject(Connector.class);
//        this.bookDao = myContext.getObject(BookDao.class);
//        this.orderDao = myContext.getObject(OrderDao.class);
//        this.requestDao = myContext.getObject(RequestDao.class);
//        this.bookService = myContext.getObject(BookService.class);
//        this.orderService = myContext.getObject(OrderService.class);
//        this.requestService = myContext.getObject(RequestService.class);
//        this.facade = myContext.getObject(Facade.class);


    }
}
