package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class IdGenerator implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(IdGenerator.class.getName());
    private static Long bookId = 1L;
    private static Long orderId = 1L;
    private static Long requestId = 1L;

    public static Long generateBookId(){
        LOGGER.info("Setting bookID");
        return bookId++;
    }

    public static Long generateOrderId(){
        LOGGER.info("Setting orderID");
        return orderId++;
    }

    public static Long generateRequestId(){
        LOGGER.info("Setting requestID");
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
