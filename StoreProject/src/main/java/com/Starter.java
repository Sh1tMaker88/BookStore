package com;

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
import com.menu.MenuController;
import com.propertyInjector.ApplicationContext;
import com.propertyInjector.Runner;
import com.serialization.*;
import com.service.BookService;
import com.service.OrderService;
import com.service.RequestService;

import java.util.HashMap;
import java.util.Map;


public class Starter {
    public static void main(String[] args) {
//        ClassScanner classScanner = ClassScanner.getInstance();
//        System.out.println(classScanner.scanForClasses());

        new Initializer();
        ApplicationContext context = Runner.run("com",
                new HashMap<>(Map.of(
//                        IOrderService.class, OrderService.class,
//                        IBookService.class, BookService.class,
//                        IRequestService.class, RequestService.class,
//                        IBookDao.class, BookDao.class,
//                        IOrderDao.class, OrderDao.class,
//                        IRequestDao.class, RequestDao.class
                )));
        Facade f = context.getObject(Facade.class);
        context.getObject(Deserializator.class);
        System.out.println(f.getBookService().getBookDao().getAll());
        System.out.println(ApplicationContext.getInstance().getCache());
        BookService bs = context.getObject(BookService.class);
        System.out.println(bs.getMonthToSetBookAsUnsold());
//        System.out.println(ApplicationContext.getInstance().getObject(BookDao.class));
//        System.out.println(BookDao.class);
        MenuController.getInstance().run();
        new Serializator();
    }
}
