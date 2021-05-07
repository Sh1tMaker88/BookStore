package com.service;

import com.annotations.ClassToInjectProperty;
import com.annotations.InjectByType;
import com.annotations.InjectValueFromProperties;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.service.IBookService;
import com.api.service.IRequestService;
import com.dao.util.Connector;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.Book;
import com.model.BookStatus;
import com.model.Request;
import com.util.comparator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Singleton
@ClassToInjectProperty
public class BookService implements IBookService {

    private static final Logger LOGGER = LogManager.getLogger(BookService.class.getName());
    @InjectByType
    private IBookDao bookDao;
    @InjectByType
    private IOrderDao orderDao;
    @InjectByType
    private IRequestService requestService;

    @InjectValueFromProperties(configName = "server", propertyName = "closeRequestAfterAddingBook", type = "boolean")
    private boolean closeRequestAfterAddingBook;
    @InjectValueFromProperties
    private int monthToSetBookAsUnsold;

    public BookService() {
//        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
//        this.requestService = ApplicationContext.getInstance().getObject(RequestService.class);
//        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    @Transactional
    public Book createBook(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description, BookStatus bookStatus, LocalDate arrivalDate) {
        try {
            Book book = new Book(name, author, isbn, pageNumber, price, yearOfPublish
                    , description, bookStatus, arrivalDate);
            LOGGER.info("Creating book in data base: " + book);
            return bookDao.create(book);
        } catch (DaoException e) {
            LOGGER.warn("Method createBook failed", e);
            throw new ServiceException("Method createBook failed", e);
        }
    }

    @Override
    @Transactional
    public Book createBook(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description) {
        try {
            Book book = new Book(name, author, isbn, pageNumber, price, yearOfPublish, description);
            LOGGER.info("Creating book in data base: " + book);
            return bookDao.create(book);
        } catch (HibernateException | DaoException e) {
            LOGGER.info("Method createBook failed", e);
            throw new ServiceException("Method createBook failed", e);
        }
    }

    @Override
    @Transactional
    public Book addBookToStock(Long bookId) {
        try {
            Book book = bookDao.getById(bookId);
            book.setBookStatus(BookStatus.IN_STOCK);
            if (closeRequestAfterAddingBook) {
                if (book.getRequest() != null) {
                    Request request = book.getRequest();
                    LOGGER.info("Closing request id=" + request.getId());
                    requestService.closeRequest(request.getId());
                }
            }
            LOGGER.info("Adding book to stock " + book);
            bookDao.update(book);
            return book;
        } catch (HibernateException | DaoException e) {
            LOGGER.info("Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }


    @Override
    @Transactional
    public Book discardBook(Long bookId) {
        try {
            LOGGER.info("Discarding book with id=" + bookId);
            Book book = bookDao.getById(bookId);
            book.setBookStatus(BookStatus.OUT_OF_STOCK);
            LOGGER.info("Updating book");
            bookDao.update(book);
            return book;
        } catch (HibernateException | DaoException e) {
            LOGGER.info("Method discardBook failed", e);
            throw new ServiceException("Method discardBook failed", e);
        }
    }

    @Override
    @Transactional
    public void showDescription(Long id) {
        try {
            String description = bookDao.getDescription(id);
            LOGGER.info("Description for book with id=" + id + ":\n"
                    + description);
        } catch (HibernateException | DaoException e) {
            LOGGER.warn("Method showDescription failed", e);
            throw new ServiceException("Method showDescription failed", e);
        }
    }

    //todo this
    @Override
    @Transactional
    public List<Book> booksNotBoughtMoreThanSixMonth() {
        try {
            Set<Book> list = orderDao.getBooksThatAreNotBought(monthToSetBookAsUnsold);
            Set<Book> list2 = bookDao.getBookThatHaveNoOrdersForPeriodOfTime(monthToSetBookAsUnsold);
            list.addAll(list2);
            return new ArrayList<>(list);
        } catch (ServiceException e) {
            LOGGER.warn("Method booksNotBoughtMoreThanSixMonth failed", e);
            throw new ServiceException("Method booksNotBoughtMoreThanSixMonth failed", e);
        }
    }

    @Deprecated
    @Override
    public List<Book> sortBooksBy(BookSort bookSort) {
        List<Book> books = bookDao.getAll();
        switch (bookSort) {
            case ID:
                books.sort(new BookIdComparator());
                break;
            case NAME:
                books.sort(new BookNameComparator());
                break;
            case PRICE:
                books.sort(new BookPriceComparator());
                break;
            case AVAILABILITY:
                books.sort(new BookAvailabilityComparator());
                break;
            case YEAR_OF_PUBLISH:
                books.sort(new BookYearOfPublishComparator());
                break;
            default:
                LOGGER.warn("No such type of sort");
                throw new ServiceException("Method sortBooksBy failed");
        }
        return books;
    }
}

