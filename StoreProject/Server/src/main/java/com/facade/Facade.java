package com.facade;

import com.api.service.IBookService;
import com.api.service.IOrderService;
import com.api.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Facade {

    private final IOrderService orderService;
    private final IBookService bookService;
    private final IRequestService requestService;

    @Autowired
    public Facade(IOrderService orderService, IBookService bookService, IRequestService requestService) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.requestService = requestService;
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
