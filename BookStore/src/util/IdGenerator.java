package util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdGenerator implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(IdGenerator.class.getName());
    private static int bookId = 1;
    private static int orderId = 1;
    private static int requestId = 1;

    public static int generateBookId(){
        LOGGER.log(Level.INFO, "Setting bookID");
        return bookId++;
    }

    public static int generateOrderId(){
        LOGGER.log(Level.INFO, "Setting orderID");
        return orderId++;
    }

    public static int generateRequestId(){
        LOGGER.log(Level.INFO, "Setting requestID");
        return requestId++;
    }

    public static void setBookId(int bookId) {
        IdGenerator.bookId = bookId;
    }

    public static void setOrderId(int orderId) {
        IdGenerator.orderId = orderId;
    }

    public static void setRequestId(int requestId) {
        IdGenerator.requestId = requestId;
    }

    public static int getBookId() {
        return bookId;
    }

    public static int getOrderId() {
        return orderId;
    }

    public static int getRequestId() {
        return requestId;
    }
}
