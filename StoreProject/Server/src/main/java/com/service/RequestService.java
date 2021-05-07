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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;

@Singleton
public class RequestService implements IRequestService {

    private static final Logger LOGGER = LogManager.getLogger(RequestService.class.getName());
    @InjectByType
    private  IRequestDao requestDao;
    @InjectByType
    private  IBookDao bookDao;

    public RequestService() {
//        this.requestDao = ApplicationContext.getInstance().getObject(RequestDao.class);
//        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
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
    @Transactional
    public Request closeRequest(Long requestID) {
        try {
            LOGGER.info("Closing request with id=" + requestID);
            Request request = getById(requestID);
            request.setRequestStatus(RequestStatus.CLOSED);
            Book book = request.getBook();
            book.setBookStatus(BookStatus.IN_STOCK);
            request.setBook(book);
            return requestDao.update(request);
        } catch (HibernateException | DaoException e) {
            LOGGER.warn("Method closeRequest failed", e);
            throw new ServiceException("Method closeRequest failed", e);
        }

//        Session session = factory.openSession();
//        session.beginTransaction();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaUpdate<Request> update = criteriaBuilder.createCriteriaUpdate(Request.class);
//        Root<Request> request = update.from(Request.class);
//
//        Subquery<Book> subquery = update.subquery(Book.class);
//        Root<Book> bookRoot = subquery.from(Book.class);
//        subquery.select(bookRoot.get("book"));
//        Path<Book> path =
//
//        update.set(request.get(Request_.book).get(Book_.bookStatus), BookStatus.IN_STOCK)
//                .set(request.get(Request_.requestStatus), RequestStatus.CLOSED)
//                .where(criteriaBuilder.equal(request.get(Request_.id), requestID))
//                .where(criteriaBuilder.in(request.get()).value(subquery));
//        Query query = session.createQuery(update);
//        query.executeUpdate();
//        session.getTransaction().commit();
//        session.close();
//        return getById(requestID);

    }

    @Override
    @Transactional
    public Request addRequest(Long bookId) {
        try {
            LOGGER.info("Adding request for book with id=" + bookId);
            Request request;
            if (requestDao.checkIfRequestExist(bookId)) {
                request = requestDao.getRequestByBookId(bookId);
                request.setRequestCount(request.getRequestCount() + 1);
                LOGGER.info("Request is already exists, increasing its counter to " + request.getRequestCount());
                requestDao.update(request);
            } else {
                Book book = bookDao.getById(bookId);
                request = new Request(book);
                requestDao.create(request);
                LOGGER.info("Created request" + request);
            }
            return request;
        } catch (HibernateException | DaoException e) {
            LOGGER.warn("Method addRequest failed", e);
            throw new ServiceException("Method addRequest failed", e);
        }
    }

    @Deprecated
    @Override
    public List<Request> sortRequestBy(RequestSort requestSort) {
        List<Request> list = requestDao.getAll();
        switch (requestSort) {
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
                LOGGER.warn("No such type of sort");
                throw new ServiceException("Method sortRequestBy failed");
        }
        return list;
    }
}
