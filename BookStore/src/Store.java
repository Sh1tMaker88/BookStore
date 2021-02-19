import api.dao.IBookDao;
import api.dao.IOrderDao;
import api.dao.IRequestDao;
import api.service.IBookService;
import api.service.IOrderService;
import api.service.IRequestService;
import dao.BookDao;
import dao.OrderDao;
import dao.RequestDao;
import models.Book;
import service.BookService;
import service.BookSort;
import service.OrderService;
import service.RequestService;

public class Store {

    private static final IBookDao bookDao = new BookDao();
    private static final IRequestDao requestDao = new RequestDao();
    private static final IOrderDao orderDao = new OrderDao();
    private static final IRequestService requestService = new RequestService(requestDao, bookDao);
    private static final IBookService bookService = new BookService(bookDao, requestDao);
    private static final IOrderService orderService = new OrderService(orderDao, requestDao);


    public static void main(String[] args) {

        Book book1 = bookService.addBookToStock
                ("King", "Arthur", 2001,43.2, "2342345", 522);
        Book book2 = bookService.addBookToStock
                ("Gilead", "Stiven", 1963,35.5,"423asdf45", 443);
        Book book3 = bookService.addBookToStock
                ("Harry Potter", "Joanne Rowling", 1996,48.0,"42s3dsaf45", 840);
        Book book4 = bookService.addBookToStock
                ("Code Complete", "McConnell", 2004,52.5," 0-7356-1967-0", 869);

        System.out.println(bookDao.getAll());
        System.out.println(bookService.sortBooksBy(BookSort.NAME));

        System.out.println(bookDao.getAll());
    }
}
