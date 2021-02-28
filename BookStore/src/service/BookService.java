package service;

import api.dao.IBookDao;
import api.dao.IRequestDao;
import api.service.IBookService;
import dao.BookDao;
import dao.RequestDao;
import exceptions.DaoException;
import exceptions.ServiceException;
import models.Book;
import models.BookStatus;
import util.IdGenerator;
import util.comparators.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookService implements IBookService {

    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    private static BookService instance;
    private final IBookDao bookDao;
    private final IRequestDao requestDao;

    private BookService() {
        this.bookDao = BookDao.getInstance();
        this.requestDao = RequestDao.getInstance();
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
            book.setId(IdGenerator.generateBookId());
            if (requestDao.getAll().stream().anyMatch(e -> e.getBook().equals(book))) {
                RequestService requestService = RequestService.getInstance();
                requestService.closeRequest(book.getId());
            }
            LOGGER.log(Level.INFO, "Creating book");
            bookDao.create(book);
            return book;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book addBookToStock(Book book) {
        try {
            book.setId(IdGenerator.generateBookId());
            if (requestDao.getAll().stream().anyMatch(e -> e.getBook().equals(book))) {
                RequestService requestService = RequestService.getInstance();
                requestService.closeRequest(book.getId());
            }
            LOGGER.log(Level.INFO, "Creating book");
            bookDao.create(book);
            return book;
        } catch (DaoException e) {
            LOGGER.log(Level.WARNING, "Method addBookToStock failed", e);
            throw new ServiceException("Method addBookToStock failed", e);
        }
    }

    @Override
    public Book discardBook(int bookId) {
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
            LocalDate lc = LocalDate.now().minusMonths(6);
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

