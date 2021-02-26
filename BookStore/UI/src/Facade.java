package src;


import api.service.IBookService;
import api.service.IOrderService;
import api.service.IRequestService;
import service.BookService;
import service.OrderService;
import service.RequestService;

import java.util.Objects;

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
        return Objects.requireNonNullElse(instance, new Facade());
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
