package api.service;

import models.Book;
import models.Order;
import models.Request;
import service.OrderSort;
import service.RequestSort;

import java.util.List;

public interface IRequestService {
    Book closeRequest(int requestID);

    void closeRequest(Book book);

    Request addRequest(Book book);

//    List<Request> listOfRequestToBook(Book book);

    List<Request> sortRequestBy(RequestSort requestSort);
}
