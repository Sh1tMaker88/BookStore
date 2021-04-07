package com.facade;

import com.api.service.IBookService;
import com.api.service.IOrderService;
import com.api.service.IRequestService;
import com.service.BookService;
import com.service.OrderService;
import com.service.RequestService;

public class Facade {

    private static Facade instance;
    private IOrderService orderService;
    private IBookService bookService;
    private IRequestService requestService;

    private Facade() {
        this.bookService = BookService.getInstance();
        this.orderService = OrderService.getInstance();
        this.requestService = RequestService.getInstance();
    }

    public static Facade getInstance(){
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
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
