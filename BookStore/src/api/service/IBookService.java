package api.service;

import models.Book;
import models.BookStatus;
import service.BookSort;

import java.util.List;

public interface IBookService {
    Book addBookToStock(String name, String author, int yearOfPublish, double price, String isbn, int pageNumber);

    Book discardBook(int bookId, BookStatus status);

    void showDescription(Book book);

    List<Book> booksNotBoughtMoreThanSixMonth();

    List<Book> sortBooksBy(BookSort bookSort);

}
