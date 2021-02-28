package util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IdGenerator {

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
}
