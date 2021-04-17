package com.api.service;

import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.models.Book;
import com.models.Order;
import com.models.Request;
import com.service.RequestSort;

import java.util.List;

public interface IRequestService {

    List<Request> getAllRequests();

    Request getById(Long id);

    Book closeRequest(Long requestID);

    void closeRequest(Book book);

    Request addRequest(Book book);

//    List<Request> listOfRequestToBook(Book book);

    List<Request> sortRequestBy(RequestSort requestSort);
}
