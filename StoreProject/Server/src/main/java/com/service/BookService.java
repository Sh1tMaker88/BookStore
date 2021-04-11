package com.service;

import com.annotations.ClassToInjectProperty;
import com.annotations.InjectValueFromProperties;
import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.api.service.IBookService;
import com.dao.BookDao;
import com.dao.RequestDao;
import com.exceptions.DaoException;
import com.exceptions.ServiceException;
import com.models.Book;
import com.models.BookStatus;
import com.propertyInjector.PropertyInjector;
import com.util.IdGenerator;
import com.util.comparators.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//todo delete getters for com.dao

@Singleton
@ClassToInjectProperty
public class BookService implements IBookService {

    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    private static BookService instance;
    private final IBookDao bookDao;
    private final IRequestDao requestDao;

    @InjectValueFromProperties(configName = "server", propertyName = "closeRequestAfterAddingBook", type = "boolean")
    public boolean closeRequestAfterAddingBook;
    @InjectValueFromProperties
    public int monthToSetBookAsUnsold;

    private BookService() {
        this.bookDao = BookDao.getInstance();
        this.requestDao = RequestDao.getInstance();

//        try {
//            FileInputStream fis  = new FileInputStream("Server/src/main/resources/myProp.properties");
//            Properties prop = new Properties();
//            prop.load(fis);
//            this.closeRequestAfterAddingBook =
//                    Boolean.parseBoolean
//                            (prop.getProperty("CLOSE_REQUEST_AFTER_ADDING_BOOK", "false"));
//            this.monthToSetBookAsUnsold = Integer.parseInt(prop.getProperty("UNSOLD_BOOK_MONTH", "-1"));
//        } catch (IOException e) {
//            LOGGER.log(Level.WARNING, "Properties file not found");
//        }
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public IBookDao getBookDao() {
        return bookDao;
    }

    public IRequestDao getRequestDao() {
        return requestDao;
    }

    @Override
    public Book addBookToStock(String name, String author, int yearOfPublish, double price, String isbn, int pageNumber) {
        try {
            Book book = new Book(name, author, yearOfPublish, price, isbn, pageNumber);
            return addBookToStock(book);
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book addBookToStock(Book book) {
        try {
            book.setId(IdGenerator.generateBookId());
            if (closeRequestAfterAddingBook) {
                if (requestDao.getAll().stream().anyMatch(e -> e.getBook().equals(book))) {
                    RequestService requestService = RequestService.getInstance();
                    requestService.closeRequest(book.getId());
                }
            }
            LOGGER.log(Level.INFO, "Creating book" + book);
            bookDao.create(book);
            return book;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
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
    public void showDescription(Book book) {
        System.out.println("Description of " + book.getName() + ":\n" + book.getDescription());
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
            throw e;
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

