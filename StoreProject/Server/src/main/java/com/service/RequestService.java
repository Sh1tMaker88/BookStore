package com.service;

import com.annotations.InjectByType;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.api.service.IRequestService;
import com.dao.BookDao;
import com.dao.RequestDao;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.models.*;
import com.propertyInjector.ApplicationContext;
import com.propertyInjector.PropertyInjector;
import com.util.IdGenerator;
import com.util.comparators.RequestAlphabeticalComparator;
import com.util.comparators.RequestIdComparator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class RequestService implements IRequestService {

    private static final Logger LOGGER = Logger.getLogger(RequestService.class.getName());
//    private static RequestService instance;
    @InjectByType
    private final IRequestDao requestDao;
    @InjectByType
    private final IBookDao bookDao;

    public RequestService() {
        this.requestDao = ApplicationContext.getInstance().getObject(RequestDao.class);
        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
    }

//    public static RequestService getInstance() {
//        if (instance == null) {
//            instance = new RequestService();
//        }
//        return instance;
//    }

    public IRequestDao getRequestDao() {
        return requestDao;
    }

    public IBookDao getBookDao() {
        return bookDao;
    }

    @Override
    public Book closeRequest(Long requestID) {
        try {
            LOGGER.log(Level.INFO, "Closing request with id=" + requestID);
            Request request = requestDao.getById(requestID);
            Book book = bookDao.getAll().stream()
                    .filter(e -> e.equals(request.getBook()))
                    .findFirst()
                    .get();
            Book realBook = bookDao.getById(book.getId());
            realBook.setBookStatus(BookStatus.IN_STOCK);
            bookDao.update(realBook);
            Request realRequest = requestDao.getById(request.getId());
            realRequest.setRequestStatus(RequestStatus.CLOSED);
            requestDao.update(realRequest);
            LOGGER.log(Level.INFO, "Request with id=" + requestID + " closed");
            return book;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method closeRequest failed", e);
            throw new ServiceException("Method closeRequest failed", e);
        }
    }

    @Override
    public void closeRequest(Book book) {
        try {
            LOGGER.log(Level.INFO, "Closing request for book " + book);
            if (requestDao.getAll().stream().anyMatch(e->e.getBook().equals(book))
                    && book.getBookStatus().equals(BookStatus.OUT_OF_STOCK)){
                Book realBook = bookDao.getById(book.getId());
                Request request = requestDao.getAll().stream()
                        .filter(e->e.equals(book))
                        .findFirst()
                        .get();
                Request realRequest = requestDao.getById(request.getId());
                realBook.setBookStatus(BookStatus.IN_STOCK);
                bookDao.update(realBook);
                realRequest.setRequestStatus(RequestStatus.CLOSED);
                requestDao.update(realRequest);
            } else {
                LOGGER.log(Level.INFO, "There is no request for book with id=" + book.getId() + "or its in stock");
            }
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method closeRequest failed", e);
            throw new ServiceException("Method closeRequest failed", e);
        }

    }

    @Override
    public Request addRequest(Book book) {
        try {
            LOGGER.log(Level.INFO, "Adding request for book with id=" + book.getId());
            Request request = new Request(book);
            if (bookDao.getAll().contains(book) &&
            book.getBookStatus().equals(BookStatus.IN_STOCK)){
//                Book b = bookDao.getAll().stream()
//                        .filter(e->e.getId()==book.getId())
//                        .findFirst().get();
//                Book realBook = bookDao.getById(b.getId());
//                realBook.setBookStatus(BookStatus.OUT_OF_STOCK);
//                bookDao.update(realBook);
                LOGGER.log(Level.INFO, "Book with id=" + book.getId() + " is already in stock");
            } else {
                request.setId(IdGenerator.generateRequestId());
                requestDao.create(request);
            }
            return request;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addRequest failed", e);
            throw new ServiceException("Method addRequest failed", e);
        }

    }

    @Override
    public List<Request> sortRequestBy(RequestSort requestSort) {
        List<Request> list = requestDao.getAll();
        switch (requestSort){
            case ID:
                list.sort(new RequestIdComparator());
                break;
            case ALPHABETICAL:
                list.sort(new RequestAlphabeticalComparator());
                break;
            case NUMBERS:
//                list.sort(new RequestNumberOfRequestsComparator());
                break;
            default:
                LOGGER.log(Level.WARNING, "No such type of sort");
                throw new ServiceException("Method sortRequestBy failed");
        }
        return list;
    }
}
