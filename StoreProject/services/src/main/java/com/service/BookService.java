package com.service;

import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.service.IBookService;
import com.api.service.IRequestService;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.Book;
import com.model.BookStatus;
import com.model.Request;
import com.sorting.BookSort;
import com.util.comparator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BookService implements IBookService {

    private static final Logger LOGGER = LogManager.getLogger(BookService.class.getName());
    private final IBookDao bookDao;
    private final IOrderDao orderDao;
    private final IRequestService requestService;

    @Value("${CLOSE_REQUEST_AFTER_ADDING_BOOK}")
    private boolean closeRequestAfterAddingBook;
    @Value("${MONTH_TO_SET_BOOK_AS_UNSOLD}")
    private int monthToSetBookAsUnsold;

    @Autowired
    public BookService(IBookDao bookDao, IOrderDao orderDao, IRequestService requestService) {
        this.bookDao = bookDao;
        this.orderDao = orderDao;
        this.requestService = requestService;
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
    public void saveBook(Book book) {
        bookDao.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }

    @Override
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

    //todo rework
    @Override
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

