package service;

import api.dao.IBookDao;
import api.dao.IRequestDao;
import api.service.IBookService;
import models.Book;
import models.BookStatus;
import util.IdGenerator;
import util.comparators.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BookService implements IBookService {
    private final IBookDao bookDao;
    private final IRequestDao requestDao;

    public BookService(IBookDao bookDao, IRequestDao requestDao) {
        this.bookDao = bookDao;
        this.requestDao = requestDao;
    }

    @Override
    //fix request stuff
    public Book addBookToStock(String name, String author, int yearOfPublish, double price, String isbn, int pageNumber) {
        Book book = new Book(name, author, yearOfPublish, price, isbn, pageNumber);
        book.setId(IdGenerator.generateBookId());
        if (requestDao.getAll().stream().anyMatch(e->e.getBook().equals(book))){
            RequestService requestService = new RequestService(requestDao, bookDao);
            requestService.closeRequest(book.getId());
        }
        bookDao.create(book);
        return book;
    }

    @Override
    public Book discardBook(int bookId, BookStatus status) {
        Book book = bookDao.getById(bookId);
        book.setBookStatus(status);
        bookDao.update(book);
        return book;
    }

    @Override
    public void showDescription(Book book) {
        System.out.println("Description of " + book.getName() + ":\n" + book.getDescription());
    }

    @Override
    public List<Book> booksNotBoughtMoreThanSixMonth() {
        List<Book> list = bookDao.getAll();
        LocalDate lc = LocalDate.now().minusMonths(6);
        list = list.stream().filter(e -> e.getArrivalDate().compareTo(lc) < 0)
                .collect(Collectors.toList());
        return list;
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
        }
        return books;
    }
}

