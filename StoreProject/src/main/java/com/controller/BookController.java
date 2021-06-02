package com.controller;

import com.api.service.IBookService;
import com.controller.configuration.NoSuchEntityException;
import com.model.Book;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> showAllBooks() {
        log.info("Received GET request /books");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> showBook(@PathVariable Long id) {
        log.info("Received GET request /books/" + id);
        if (bookService.getById(id) == null) {
            throw new NoSuchEntityException("There is no book with id=" + id + " in database");
        }
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        log.info("Received POST request /books");
        bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestParam Long id, @RequestBody Book book) {
        log.info("Received Patch request /books/" + id);
        bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        log.info("Received PUT request /books");
        bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        log.info("Received DELETE request /books/" + id);
        if (bookService.getById(id) == null) {
            throw new NoSuchEntityException("There is no book with id=" + id + " in database");
        } else {
            bookService.deleteBook(id);
            return "Book with id=" + id + " was deleted";
        }
    }

    @PutMapping("/add-to-stock/{id}")
    public ResponseEntity<Book> addBookToStock(@PathVariable Long id) {
        log.info("Received PUT request /books/" + id + "/add-to-stock");
        return ResponseEntity.ok(bookService.addBookToStock(id));
    }

    @PutMapping("/discard/{id}")
    public ResponseEntity<Book> discardBook(@PathVariable Long id) {
        log.info("Received PUT request /books/" + id + "/discard");
        return ResponseEntity.ok(bookService.discardBook(id));
    }

    @GetMapping("/poor-purchased")
    public ResponseEntity<List<Book>> getPoorPurchasedBooks() {
        log.info("Received GET request /books/poor-purchased");
        return ResponseEntity.ok(bookService.booksNotBoughtMoreThanSixMonth());
    }

//    @GetMapping
//    public ResponseEntity<List<Book>> getAll(@RequestParam(name = "sort", defaultValue = "id") String sort,
//                                             @RequestParam(name = "start", defaultValue = "1") Integer start,
//                                             @RequestParam(name = "limit", defaultValue = "5") Integer limit) {
//
//    }
}
