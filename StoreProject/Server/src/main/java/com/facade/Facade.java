package com.facade;

import com.annotations.InjectByType;
import com.annotations.Singleton;
import com.api.service.IBookService;
import com.api.service.IOrderService;
import com.api.service.IRequestService;
import com.propertyInjector.ApplicationContext;
import com.service.BookService;
import com.service.OrderService;
import com.service.RequestService;

@Singleton
public class Facade {

    @InjectByType
    private final IOrderService orderService;
    @InjectByType
    private final IBookService bookService;
    @InjectByType
    private final IRequestService requestService;

    public Facade() {
        this.bookService = ApplicationContext.getInstance().getObject(BookService.class);
        this.orderService = ApplicationContext.getInstance().getObject(OrderService.class);
        this.requestService = ApplicationContext.getInstance().getObject(RequestService.class);
    }

    public IOrderService getOrderService() {
        return orderService;
    }

    public IBookService getBookService() {
        return bookService;
    }

    public IRequestService getRequestService() {
        return requestService;
    }


}
