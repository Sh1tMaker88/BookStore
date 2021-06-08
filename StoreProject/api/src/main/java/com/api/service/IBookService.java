package com.api.service;

import com.model.Book;
import com.model.BookStatus;
import com.sorting.BookSort;

import java.time.LocalDate;
import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();

    Book getById(Long id);

    Book createBook(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description, BookStatus bookStatus, LocalDate arrivalDate);

    Book createBook(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description);

    void saveBook(Book book);

    void deleteBook(Long id);

    Book addBookToStock(Long bookId);

    Book discardBook(Long bookId);

    void showDescription(Long id);

    List<Book> booksNotBoughtMoreThanSixMonth();

    List<Book> sortBooksBy(BookSort bookSort);
}
