package com.serialization;

import com.annotations.InjectByType;
import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.dao.IRequestDao;
import com.dao.BookDao;
import com.dao.OrderDao;
import com.dao.RequestDao;
import com.models.Book;
import com.models.Order;
import com.models.Request;
import com.propertyInjector.ApplicationContext;
import com.service.BookService;
import com.service.OrderService;
import com.service.RequestService;
import com.util.IdGenerator;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deserializator {

    private static final Logger LOGGER = Logger.getLogger(Deserializator.class.getName());
//    @InjectByType
    private final IBookDao bookDao;
//    @InjectByType
    private final IOrderDao orderDao;
//    @InjectByType
    private final IRequestDao requestDao;

    public Deserializator() {
        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
        this.orderDao = ApplicationContext.getInstance().getObject(OrderDao.class);
        this.requestDao = ApplicationContext.getInstance().getObject(RequestDao.class);
        deserializeBooks();

        deserializeRequests();

        deserializeOrders();
        LOGGER.log(Level.INFO, "Deserialization completed");
    }

    public void deserializeBooks() {
        try (ObjectInputStream inputStreamBooks = new ObjectInputStream(
                new FileInputStream("serializationFiles/books.bin"));
             ObjectInputStream inputStreamBooksId = new ObjectInputStream
                     (new FileInputStream("serializationFiles/booksId.bin"))) {
            Map<Book, Long> idMap = (Map<Book, Long>) inputStreamBooksId.readObject();
            while (true) {
                Object bookObj = inputStreamBooks.readObject();
                if (bookObj instanceof Book) {
                    for (Map.Entry<Book, Long> entry : idMap.entrySet()) {
                        if (bookObj.equals(entry.getKey())) {
                            ((Book) bookObj).setId(entry.getValue());
                        }
                    }
                    bookDao.create((Book) bookObj);
                    IdGenerator.setBookId(IdGenerator.getBookId() + 1);
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Deserialization of books failed", e);
            e.printStackTrace();
        } catch (IOException e) {
        }
    }

    public void deserializeRequests() {
        try (ObjectInputStream inputStreamRequests = new ObjectInputStream
                (new FileInputStream("serializationFiles/requests.bin"));
             ObjectInputStream inputStreamRequestsId = new ObjectInputStream
                     (new FileInputStream("serializationFiles/requestsId.bin"))) {
            Map<Request, Long> idMap = (Map<Request, Long>) inputStreamRequestsId.readObject();
            while (true) {
                Object requestObj = inputStreamRequests.readObject();
                if (requestObj instanceof Request) {
                    for (Map.Entry<Request, Long> entry : idMap.entrySet()) {
                        if (requestObj.equals(entry.getKey())) {
                            ((Request) requestObj).setId(entry.getValue());
                        }
                    }
                    requestDao.create((Request) requestObj);
                    IdGenerator.setRequestId(IdGenerator.getRequestId() + 1);
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Deserialization of request failed", e);
            e.printStackTrace();
        } catch (IOException e) {
        }
    }

    public void deserializeOrders() {
        try (ObjectInputStream inputStreamOrders = new ObjectInputStream
                (new FileInputStream("serializationFiles/orders.bin"));
             ObjectInputStream inputStreamOrdersId = new ObjectInputStream
                     (new FileInputStream("serializationFiles/ordersId.bin"));
             ObjectInputStream inputStreamOrderBooksId = new ObjectInputStream
                     (new FileInputStream("serializationFiles/booksId.bin"))) {
            Map<Order, Long> idMapOrders = (Map<Order, Long>) inputStreamOrdersId.readObject();
            Map<Book, Long> idMapBooks = (Map<Book, Long>) inputStreamOrderBooksId.readObject();
            while (true) {
                Object orderObj = inputStreamOrders.readObject();
                if (orderObj instanceof Order) {
                    //setting ID to order
                    for (Map.Entry<Order, Long> entry : idMapOrders.entrySet()) {
                        if (orderObj.equals(entry.getKey())) {
                            ((Order) orderObj).setId(entry.getValue());
                        }
                    }
                    //setting ID to books in order
                    for (Book b : ((Order) orderObj).getBooks()) {
                        for (Map.Entry<Book, Long> entry : idMapBooks.entrySet()){
                            if (b.equals(entry.getKey())){
                                b.setId(entry.getValue());
                            }
                        }
                    }
                    orderDao.create((Order) orderObj);
                    IdGenerator.setOrderId(IdGenerator.getOrderId() + 1);
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Deserialization of orders failed", e);
            e.printStackTrace();
        } catch (IOException e) {
        }
    }
}