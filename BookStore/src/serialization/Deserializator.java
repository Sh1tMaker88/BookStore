package serialization;

import api.dao.IBookDao;
import api.dao.IOrderDao;
import api.dao.IRequestDao;
import models.Book;
import models.Order;
import models.Request;
import src.Facade;
import util.IdGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deserializator {

    private static final Logger LOGGER = Logger.getLogger(Deserializator.class.getName());
    private final Facade facade = Facade.getInstance();
    private final IBookDao bookDao = facade.getBookService().getBookDao();
    private final IOrderDao orderDao = facade.getOrderService().getOrderDao();
    private final IRequestDao requestDao = facade.getRequestService().getRequestDao();

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
                new FileInputStream("resources/serializationFiles/books.bin"));
             ObjectInputStream inputStreamBooksId = new ObjectInputStream
                     (new FileInputStream("resources/serializationFiles/booksId.bin"))) {
            Map<Book, Integer> idMap = (Map<Book, Integer>) inputStreamBooksId.readObject();
            while (true) {
                Object bookObj = inputStreamBooks.readObject();
                if (bookObj instanceof Book) {
                    for (Map.Entry<Book, Integer> entry : idMap.entrySet()) {
                        if (((Book) bookObj).getName().equals(entry.getKey().getName()) &&
                                ((Book) bookObj).getIsbn().equals(entry.getKey().getIsbn())) {
                            ((Book) bookObj).setId(entry.getValue());
                        }
                    }
                    bookDao.create((Book) bookObj);
                    IdGenerator.setBookId(IdGenerator.getBookId() + 1);
                }

            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reach end of deserialization of books");
        }
    }

    public void deserializeRequests() {
        try (ObjectInputStream inputStreamRequests = new ObjectInputStream
                (new FileInputStream("resources/serializationFiles/requests.bin"));
             ObjectInputStream inputStreamRequestsId = new ObjectInputStream
                     (new FileInputStream("resources/serializationFiles/requestsId.bin"))) {
            Map<Book, Integer> idMap = (Map<Book, Integer>) inputStreamRequestsId.readObject();
            while (true) {
                Object requestObj = inputStreamRequests.readObject();
                if (requestObj instanceof Request) {
                    for (Map.Entry<Book, Integer> entry : idMap.entrySet()) {
                        if (((Request) requestObj).getBook().getName().equals(entry.getKey().getName()) &&
                                ((Request) requestObj).getBook().getIsbn().equals(entry.getKey().getIsbn())) {
                            ((Request) requestObj).setId(entry.getValue());
                        }
                    }
                    requestDao.create((Request) requestObj);
                    IdGenerator.setRequestId(IdGenerator.getRequestId() + 1);
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reach end of deserialization of requests");
        }
    }

    public void deserializeOrders() {
        try (ObjectInputStream inputStreamOrders = new ObjectInputStream
                (new FileInputStream("resources/serializationFiles/orders.bin"));
             ObjectInputStream inputStreamOrdersId = new ObjectInputStream
                     (new FileInputStream("resources/serializationFiles/ordersId.bin"));
             ObjectInputStream inputStreamOrderBooksId = new ObjectInputStream
                     (new FileInputStream("resources/serializationFiles/booksId.bin"))) {
            Map<Order, Integer> idMapOrders = (Map<Order, Integer>) inputStreamOrdersId.readObject();
            Map<Book, Integer> idMapBooks = (Map<Book, Integer>) inputStreamOrderBooksId.readObject();
            while (true) {
                Object orderObj = inputStreamOrders.readObject();
                if (orderObj instanceof Order) {
                    //setting ID to order
                    for (Map.Entry<Order, Integer> entry : idMapOrders.entrySet()) {
                        if (((Order) orderObj).getCustomerName().equals(entry.getKey().getCustomerName()) &&
                                ((Order) orderObj).getOrderDate().equals(entry.getKey().getOrderDate()) &&
                                ((Order) orderObj).getTotalPrice() == entry.getKey().getTotalPrice()) {
                            ((Order) orderObj).setId(entry.getValue());
                        }
                    }
                    //setting ID to books in order
                    for (Book b : ((Order) orderObj).getBooks()) {
                        for (Map.Entry<Book, Integer> entry : idMapBooks.entrySet()){
                            if (b.getName().equals(entry.getKey().getName()) &&
                                    b.getIsbn().equals(entry.getKey().getIsbn())){
                                b.setId(entry.getValue());
                            }
                        }
                    }
                    orderDao.create((Order) orderObj);
                    IdGenerator.setOrderId(IdGenerator.getOrderId() + 1);
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reach end of deserialization of orders");
        }
    }
}