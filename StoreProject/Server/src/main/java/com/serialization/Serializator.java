package com.serialization;

import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.dao.IRequestDao;
import com.dao.BookDao;
import com.dao.OrderDao;
import com.dao.RequestDao;
import com.model.Book;
import com.model.Order;
import com.model.Request;
import com.propertyInjector.ApplicationContext;

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
//    @InjectByType
    private final IBookDao bookDao;
//    @InjectByType
    private final IOrderDao orderDao;
//    @InjectByType
    private final IRequestDao requestDao;
//    Facade f = ApplicationContext.getInstance().getObject(Facade.class);

    public Serializator() {
        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
        this.orderDao = ApplicationContext.getInstance().getObject(OrderDao.class);
        this.requestDao = ApplicationContext.getInstance().getObject(RequestDao.class);
        bookSerialization();

        requestSerialization();

        orderSerialization();
    }

    public void bookSerialization(){
        try (ObjectOutputStream outputStreamBooks = new ObjectOutputStream
                (new FileOutputStream("serializationFiles/books.bin"));
        ObjectOutputStream outputStreamBooksId = new ObjectOutputStream
                (new FileOutputStream("serializationFiles/booksId.bin"))){
            List<Book> books = bookDao.getAll();
            Map<Book, Long> idMap = new HashMap<>();
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
                (new FileOutputStream("serializationFiles/requests.bin"));
        ObjectOutputStream outputStreamRequestsId = new ObjectOutputStream
                (new FileOutputStream("serializationFiles/requestsId.bin"))){
            List<Request> requests = requestDao.getAll();
            Map<Request, Long> idMap = new HashMap<>();
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
                (new FileOutputStream("serializationFiles/orders.bin"));
        ObjectOutputStream outputStreamOrdersId = new ObjectOutputStream
                (new FileOutputStream("serializationFiles/ordersId.bin"))){
            List<Order> orders = orderDao.getAll();
            Map<Order, Long> idMap = new HashMap<>();
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
