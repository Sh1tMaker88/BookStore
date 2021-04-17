package com.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdGenerator implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(IdGenerator.class.getName());
    private static Long bookId = 1L;
    private static Long orderId = 1L;
    private static Long requestId = 1L;

    public static Long generateBookId(){
        LOGGER.log(Level.INFO, "Setting bookID");
        return bookId++;
    }

    public static Long generateOrderId(){
        LOGGER.log(Level.INFO, "Setting orderID");
        return orderId++;
    }

    public static Long generateRequestId(){
        LOGGER.log(Level.INFO, "Setting requestID");
        return requestId++;
    }

    public static void setBookId(Long bookId) {
        IdGenerator.bookId = bookId;
    }

    public static void setOrderId(Long orderId) {
        IdGenerator.orderId = orderId;
    }

    public static void setRequestId(Long requestId) {
        IdGenerator.requestId = requestId;
    }

    public static Long getBookId() {
        return bookId;
    }

    public static Long getOrderId() {
        return orderId;
    }

    public static Long getRequestId() {
        return requestId;
    }
}
