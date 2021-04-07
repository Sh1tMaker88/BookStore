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
import util.IdGenerator;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deserializator {

    private static final Logger LOGGER = Logger.getLogger(Deserializator.class.getName());
    private final IBookDao bookDao = BookService.getInstance().getBookDao();
    private final IOrderDao orderDao = OrderService.getInstance().getOrderDao();
    private final IRequestDao requestDao = RequestService.getInstance().getRequestDao();

    public Deserializator() {
        deserializeBooks();
        LOGGER.log(Level.INFO, "Book deserialization completed");

        deserializeRequests();
        LOGGER.log(Level.INFO, "Request deserialization completed");

        deserializeOrders();
        LOGGER.log(Level.INFO, "Order deserialization completed");
    }

    public void deserializeBooks() {
        try (ObjectInputStream inputStreamBooks = new ObjectInputStream(
                new FileInputStream("Server/src/main/resources/serializationFiles/books.bin"));
             ObjectInputStream inputStreamBooksId = new ObjectInputStream
                     (new FileInputStream("Server/src/main/resources/serializationFiles/booksId.bin"))) {
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
                (new FileInputStream("Server/src/main/resources/serializationFiles/requests.bin"));
             ObjectInputStream inputStreamRequestsId = new ObjectInputStream
                     (new FileInputStream("Server/src/main/resources/serializationFiles/requestsId.bin"))) {
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
                (new FileInputStream("Server/src/main/resources/serializationFiles/orders.bin"));
             ObjectInputStream inputStreamOrdersId = new ObjectInputStream
                     (new FileInputStream("Server/src/main/resources/serializationFiles/ordersId.bin"));
             ObjectInputStream inputStreamOrderBooksId = new ObjectInputStream
                     (new FileInputStream("Server/src/main/resources/serializationFiles/booksId.bin"))) {
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