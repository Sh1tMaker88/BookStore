package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Book extends AIdentity implements Serializable {

    static final long serialVersionUID = 3L;
    private String name;
    private String author;
    private int yearOfPublish;
    private int pageNumber;
    private String isbn;
    private double price;
    private BookStatus bookStatus;
    private String description = "";
    private LocalDate arrivalDate;
    private int orderCount = 0;

    public Book() {
    }

    public Book(String name, String author, String isbn, int pageNumber
            , double price, int yearOfPublish, String description) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.pageNumber = pageNumber;
        this.price = price;
        this.yearOfPublish = yearOfPublish;
        this.description = description;
        this.bookStatus = BookStatus.IN_STOCK;
        this.arrivalDate = LocalDate.now();
    }

    public Book(String name, String author, String isbn, int pageNumber, double price
            , int yearOfPublish, String description, BookStatus bookStatus, LocalDate arrivalDate) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.pageNumber = pageNumber;
        this.bookStatus = bookStatus;
        this.price = price;
        this.yearOfPublish = yearOfPublish;
        this.description = description;
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return "Book(" + "bookId=" + getId() +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", status=" + bookStatus +
                ", price=" + price +
                ", arrivalDate=" + arrivalDate +
                ")\n";
    }
}
