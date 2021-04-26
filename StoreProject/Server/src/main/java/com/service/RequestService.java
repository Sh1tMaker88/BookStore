package com.service;

import com.annotations.InjectByType;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.api.service.IRequestService;
import com.dao.BookDao;
import com.dao.RequestDao;
import com.dao.util.Connector;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.*;
import com.propertyInjector.ApplicationContext;
import com.util.comparator.RequestCounterComparator;
import com.util.comparator.RequestIdComparator;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class RequestService implements IRequestService {

    private static final Logger LOGGER = Logger.getLogger(RequestService.class.getName());
    @InjectByType
    private final IRequestDao requestDao;
    @InjectByType
    private final IBookDao bookDao;
    @InjectByType
    private final Connector connector;
    private final String CLOSE_REQUEST_QUERY = "UPDATE request JOIN book ON request.book_id = book.id " +
            "SET request.status = 'CLOSED', book.status = 'IN_STOCK' WHERE request.id=?";
    private final String CHECK_IF_REQUEST_ON_THIS_BOOK_EXISTS_QUERY = "SELECT id, book_id FROM request WHERE book_id=?";
    private final String INCREASE_REQUEST_COUNTER_QUERY = "UPDATE request SET request_count = request_count + 1 " +
            "WHERE book_id=?";


    public RequestService() {
        this.requestDao = ApplicationContext.getInstance().getObject(RequestDao.class);
        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestDao.getAll();
    }

    @Override
    public Request getById(Long id) {
        return requestDao.getById(id);
    }

    @Override
    public Request closeRequest(Long requestID) {
        try {
            LOGGER.log(Level.INFO, "Closing request with id=" + requestID);
            Connection connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(CLOSE_REQUEST_QUERY);
            statement.setLong(1, requestID);
            statement.executeUpdate();
            Request request = getById(requestID);
            LOGGER.log(Level.INFO, request + " has been closed");
            statement.close();
            return request;

//            Request request = requestDao.getById(requestID);
//            Book book = bookDao.getAll().stream()
//                    .filter(e -> e.equals(request.getBook()))
//                    .findFirst()
//                    .get();
//            Book realBook = bookDao.getById(book.getId());
//            realBook.setBookStatus(BookStatus.IN_STOCK);
//            bookDao.update(realBook);
//            Request realRequest = requestDao.getById(request.getId());
//            realRequest.setRequestStatus(RequestStatus.CLOSED);
//            requestDao.update(realRequest);
//            LOGGER.log(Level.INFO, "Request with id=" + requestID + " closed");
//            return book;
        } catch (SQLException | DaoException e) {
            LOGGER.log(Level.WARNING, "Method closeRequest failed", e);
            throw new ServiceException("Method closeRequest failed", e);
        }
    }

    @Override
    public Request addRequest(Long bookId) {
        try {
            LOGGER.log(Level.INFO, "Adding request for book with id=" + bookId);
            Connection connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_IF_REQUEST_ON_THIS_BOOK_EXISTS_QUERY);
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LOGGER.log(Level.INFO, "Request for book id=" + bookId + " is already created. Increase counter");
                Request request = requestDao.getById(resultSet.getLong("id"));
                resultSet.close();
                statement.close();
                PreparedStatement statementForUpdate = connection.prepareStatement(INCREASE_REQUEST_COUNTER_QUERY);
                statementForUpdate.setLong(1, bookId);
                statementForUpdate.executeUpdate();
                statementForUpdate.close();
                return request;
            } else {
                //if no request for this book create new request
                LOGGER.log(Level.INFO, "Creating request for book with id=" + bookId);
                Request request = new Request(bookId);
                requestDao.create(request);
                return request;
            }
//            LOGGER.log(Level.INFO, "Adding request for book with id=" + book.getId());
//            Request request = new Request(book);
//            if (bookDao.getAll().contains(book) &&
//            book.getBookStatus().equals(BookStatus.IN_STOCK)){
////                Book b = bookDao.getAll().stream()
////                        .filter(e->e.getId()==book.getId())
////                        .findFirst().get();
////                Book realBook = bookDao.getById(b.getId());
////                realBook.setBookStatus(BookStatus.OUT_OF_STOCK);
////                bookDao.update(realBook);
//                LOGGER.log(Level.INFO, "Book with id=" + book.getId() + " is already in stock");
//            } else {
//                request.setId(IdGenerator.generateRequestId());
//                requestDao.create(request);
//            }
//            return request;
        } catch (SQLException | DaoException e) {
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
            case COUNTER:
                list.sort(new RequestCounterComparator());
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
