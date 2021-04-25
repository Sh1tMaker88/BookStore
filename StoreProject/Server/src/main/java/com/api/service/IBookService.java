package com.api.service;

import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.models.Book;
import com.models.BookStatus;
import com.service.BookSort;

import java.time.LocalDate;
import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();

    Book getById(Long id);

    Book addBookToStock(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description, BookStatus bookStatus, LocalDate arrivalDate);

    Book addBookToStock(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description);

    Book addBookToStock(Book book);

    Book discardBook(Long bookId);

    void showDescription(Long id);

    List<Book> booksNotBoughtMoreThanSixMonth();

    List<Book> sortBooksBy(BookSort bookSort);

}
