package com.api.service;

import com.model.Request;
import com.sorting.RequestSort;

import java.util.List;

public interface IRequestService {

    List<Request> getAllRequests();

    Request getById(Long id);

    Request closeRequest(Long requestID);

    Request addRequest(Long bookId);

//    List<Request> listOfRequestToBook(Book book);

    List<Request> sortRequestBy(RequestSort requestSort);
}
