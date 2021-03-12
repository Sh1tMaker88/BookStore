package serialization;

import api.dao.IBookDao;
import api.dao.IOrderDao;
import api.dao.IRequestDao;
import models.Book;
import models.Order;
import models.Request;
import service.BookService;
import service.OrderService;
import service.RequestService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serializator {

    private static final Logger LOGGER = Logger.getLogger(Serializator.class.getName());
    private final IBookDao bookDao = BookService.getInstance().getBookDao();
    private final IOrderDao orderDao = OrderService.getInstance().getOrderDao();
    private final IRequestDao requestDao = RequestService.getInstance().getRequestDao();

    public Serializator() {
        bookSerialization();

        requestSerialization();

        orderSerialization();
    }

    public void bookSerialization(){
        try (ObjectOutputStream outputStreamBooks = new ObjectOutputStream
                (new FileOutputStream("UI/src/main/resources/serializationFiles/books.bin"));
        ObjectOutputStream outputStreamBooksId = new ObjectOutputStream
                (new FileOutputStream("UI/src/main/resources/serializationFiles/booksId.bin"))){
            List<Book> books = bookDao.getAll();
            Map<Book, Integer> idMap = new HashMap<>();
            for (Book book : books) {
                outputStreamBooks.writeObject(book);
                idMap.put(book, book.getId());
            }
            outputStreamBooksId.writeObject(idMap);
            LOGGER.log(Level.INFO, "Book serialization completed");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reach end of serialization of orders");
        }
    }

    public void requestSerialization(){
        try (ObjectOutputStream outputStreamRequests = new ObjectOutputStream
                (new FileOutputStream("UI/src/main/resources/serializationFiles/requests.bin"));
        ObjectOutputStream outputStreamRequestsId = new ObjectOutputStream
                (new FileOutputStream("UI/src/main/resources/serializationFiles/requestsId.bin"))){
            List<Request> requests = requestDao.getAll();
            Map<Request, Integer> idMap = new HashMap<>();
            for (Request request : requests) {
                outputStreamRequests.writeObject(request);
                idMap.put(request, request.getId());
            }
            outputStreamRequestsId.writeObject(idMap);
            LOGGER.log(Level.INFO, "Request serialization completed");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reach end of serialization of orders");
        }
    }

    public void orderSerialization(){
        try(ObjectOutputStream outputStreamOrders = new ObjectOutputStream
                (new FileOutputStream("UI/src/main/resources/serializationFiles/orders.bin"));
        ObjectOutputStream outputStreamOrdersId = new ObjectOutputStream
                (new FileOutputStream("UI/src/main/resources/serializationFiles/ordersId.bin"))){
            List<Order> orders = orderDao.getAll();
            Map<Order, Integer> idMap = new HashMap<>();
            for (Order order : orders) {
                idMap.put(order, order.getId());
                outputStreamOrders.writeObject(order);
            }
            outputStreamOrdersId.writeObject(idMap);
            LOGGER.log(Level.INFO, "Order serialization completed");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reach end of serialization of orders");
        }
    }
}
