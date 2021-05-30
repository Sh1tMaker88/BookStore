package com.controller;

import com.api.service.IBookService;
import com.controller.configuration.NoSuchEntityException;
import com.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> showAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book showBook(@PathVariable Long id) {
        if (bookService.getById(id) == null) {
            throw new NoSuchEntityException("There is no book with id=" + id + " in database");
        }
        return bookService.getById(id);
    }

    @PostMapping("/books")
    public Book saveBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return book;
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return book;
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id) {
        if (bookService.getById(id) == null) {
            throw new NoSuchEntityException("There is no book with id=" + id + " in database");
        } else {
            bookService.deleteBook(id);
            return "Book with id=" + id + " was deleted";
        }
    }


}
