package com.api.service;

import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.models.Book;
import com.service.BookSort;

import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();

    Book getById(Long id);

    Book addBookToStock(String name, String author, int yearOfPublish, double price, String isbn, int pageNumber);

    Book addBookToStock(Book book);

    Book discardBook(Long bookId);

    void showDescription(Book book);

    List<Book> booksNotBoughtMoreThanSixMonth();

    List<Book> sortBooksBy(BookSort bookSort);

}
