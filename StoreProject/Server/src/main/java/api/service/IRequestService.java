package api.service;

import api.dao.IBookDao;
import api.dao.IRequestDao;
import models.Book;
import models.Request;
import service.RequestSort;

import java.util.List;

public interface IRequestService {

    IRequestDao getRequestDao();

    IBookDao getBookDao();

    Book closeRequest(Long requestID);

    void closeRequest(Book book);

    Request addRequest(Book book);

//    List<Request> listOfRequestToBook(Book book);

    List<Request> sortRequestBy(RequestSort requestSort);
}
