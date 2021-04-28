package com.service;

import com.annotations.ClassToInjectProperty;
import com.annotations.InjectByType;
import com.annotations.InjectValueFromProperties;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.api.service.IBookService;
import com.dao.BookDao;
import com.dao.RequestDao;
import com.dao.util.Connector;
import com.exception.DaoException;
import com.exception.ServiceException;
import com.model.Book;
import com.model.BookStatus;
import com.propertyInjector.ApplicationContext;
import com.util.comparator.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
@ClassToInjectProperty
public class BookService implements IBookService {

    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    @InjectByType
    private final IBookDao bookDao;
    @InjectByType
    private final IRequestDao requestDao;
    private final Connector connector;

    @InjectValueFromProperties(configName = "server", propertyName = "closeRequestAfterAddingBook", type = "boolean")
    private boolean closeRequestAfterAddingBook;
    @InjectValueFromProperties
    private int monthToSetBookAsUnsold;

    private final String SHOW_DESCRIPTION_QUERY = "SELECT description FROM book WHERE id=?";
    private final String CLOSE_REQUEST_AFTER_ADDING_BOOK_QUERY = "UPDATE request SET status=? WHERE book_id=?";

    public BookService() {
        this.bookDao = ApplicationContext.getInstance().getObject(BookDao.class);
        this.requestDao = ApplicationContext.getInstance().getObject(RequestDao.class);
        this.connector = ApplicationContext.getInstance().getObject(Connector.class);
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
            LOGGER.log(Level.INFO, "Creating book in data base: " + book);
            return bookDao.create(book);
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book createBook(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description) {
        try {
            Book book = new Book(name, author, isbn, pageNumber, price, yearOfPublish, description);
            LOGGER.log(Level.INFO, "Creating book in data base: " + book);
            return bookDao.create(book);
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book addBookToStock(Long bookId) {
        Book book = bookDao.getById(bookId);
        book.setBookStatus(BookStatus.IN_STOCK);
        if (closeRequestAfterAddingBook) {
            try (Connection connection = connector.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(CLOSE_REQUEST_AFTER_ADDING_BOOK_QUERY);
                statement.setString(1, "CLOSED");
                statement.setLong(2, bookId);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                throw new ServiceException("Closing request for book id=" + book.getId() + " failed", e);
            }
        }
        LOGGER.log(Level.INFO, "Adding book to stock " + book);
        bookDao.update(book);
        return book;
    }

    @Override
    public Book discardBook(Long bookId) {
        try {
            LOGGER.log(Level.INFO, "Discarding book with id=" + bookId);
            Book book = bookDao.getById(bookId);
            book.setBookStatus(BookStatus.OUT_OF_STOCK);
            LOGGER.log(Level.INFO, "Updating book");
            bookDao.update(book);
            return book;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public void showDescription(Long id) {
        Connection connection = connector.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SHOW_DESCRIPTION_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            LOGGER.log(Level.INFO, "Description for book with id=" + id + " is:\n"
                    + resultSet.getString("description"));
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Method showDescription failed", e);
            throw new ServiceException("Method showDescription failed", e);
        }
    }

    @Override
    public List<Book> booksNotBoughtMoreThanSixMonth() {
        try {
            List<Book> list = bookDao.getAll();
            LocalDate lc = LocalDate.now().minusMonths(monthToSetBookAsUnsold);
            list = list.stream().filter(e -> e.getArrivalDate().compareTo(lc) < 0)
                    .collect(Collectors.toList());
            return list;
        } catch (ServiceException e) {
            LOGGER.log(Level.WARNING, "Method booksNotBoughtMoreThanSixMonth failed", e);
            throw new ServiceException("Method booksNotBoughtMoreThanSixMonth failed", e);
        }
    }

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
                LOGGER.log(Level.WARNING, "No such type of sort");
                throw new ServiceException("Method sortBooksBy failed");
        }
        return books;
    }
}

