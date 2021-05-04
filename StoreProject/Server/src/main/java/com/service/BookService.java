package com.service;

import com.annotations.ClassToInjectProperty;
import com.annotations.InjectByType;
import com.annotations.InjectValueFromProperties;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.service.IBookService;
import com.api.service.IRequestService;
import com.dao.BookDao;
import com.dao.util.Connector;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.Book;
import com.model.BookStatus;
import com.model.Request;
import com.propertyInjector.ApplicationContext;
import com.util.comparator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@ClassToInjectProperty
public class BookService implements IBookService {

    private static final Logger LOGGER = LogManager.getLogger(BookService.class.getName());
    @InjectByType
    private final IBookDao bookDao;
    private final IRequestService requestService;
    private final Connector connector;
    SessionFactory sessionFactory;

    @InjectValueFromProperties(configName = "server", propertyName = "closeRequestAfterAddingBook", type = "boolean")
    private boolean closeRequestAfterAddingBook;
    @InjectValueFromProperties
    private int monthToSetBookAsUnsold;

    private final String SHOW_DESCRIPTION_QUERY = "SELECT description FROM book WHERE id=?";
    private final String CLOSE_REQUEST_AFTER_ADDING_BOOK_QUERY = "UPDATE request SET status=? WHERE book_id=?";
    private final String SEE_BOOKS_NOT_BOUGHT_LONG_PERIOD_QUERY = "SELECT * FROM book JOIN ordering_book " +
            "ON ordering_book.book_id=book.id " +
            "WHERE ordering_book.book_id IN  +" +
            "(SELECT book_id FROM ordering o JOIN ordering_book o_b ON o.id=o_b.order_id  +" +
            "WHERE DATEDIFF(CURDATE(), DATE_ADD(o.order_date, INTERVAL ? MONTH)) > 1  +" +
            "ORDER BY book_id)";

    public BookService() {
        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
        this.requestService = ApplicationContext.getInstance().getObject(RequestService.class);
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
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
            LOGGER.warn("Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book createBook(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description) {
        try {
            Book book = new Book(name, author, isbn, pageNumber, price, yearOfPublish, description);
            LOGGER.info("Creating book in data base: " + book);
            return bookDao.create(book);
        } catch (DaoException e) {
            LOGGER.info("Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book addBookToStock(Long bookId) {
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
        } catch (DaoException e) {
            LOGGER.info("Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    //todo this
    @Override
    public void showDescription(Long id) {
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
//        Root<Book> root = criteriaQuery.from(Book.class);
//        Selection selection = root.get("Description for book with id=");
        try {
            Session session = sessionFactory.openSession();
            String query = String.format("SELECT description FROM Book WHERE id=%d", id);
            List<String> list = session.createQuery(query).getResultList();
            LOGGER.info("Description for book with id=" + id + ":\n"
                    + list);
            session.close();
        } catch (HibernateException e) {
            LOGGER.warn("Method showDescription failed", e);
            throw new ServiceException("Method showDescription failed", e);
        }
    }

    //todo this
    @Override
    public List<Book> booksNotBoughtMoreThanSixMonth() {
        try {
            Session session = sessionFactory.openSession();
            String q = "FROM Book WHERE orders ";
            String query = String.format("FROM Book WHERE orders.getBook JOIN ordering_book o_b ON o.id=o_b.order_id ");
            List<Book> list = session.createQuery(query).getResultList();



//            List<Book> list = bookDao.getAll();
//            LocalDate lc = LocalDate.now().minusMonths(monthToSetBookAsUnsold);
//            list = list.stream().filter(e -> e.getArrivalDate().compareTo(lc) < 0)
//                    .collect(Collectors.toList());
            return list;
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

