package util;

public class IdGenerator {

    private static int bookId = 1;
    private static int orderId = 1;
    private static int requestId = 1;

    public static int generateBookId(){
        return bookId++;
    }

    public static int generateOrderId(){
        return orderId++;
    }

    public static int generateRequestId(){
        return requestId++;
    }
}
