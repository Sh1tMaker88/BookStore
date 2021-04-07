package facade;

import api.dao.IBookDao;
import api.service.IBookService;
import api.service.IOrderService;
import api.service.IRequestService;
import service.BookService;
import service.OrderService;
import service.RequestService;

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
