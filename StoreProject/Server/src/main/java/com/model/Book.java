package com.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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



    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    //todo refactor comparing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pageNumber == book.pageNumber &&
                Double.compare(book.price, price) == 0 &&
                orderCount == book.orderCount &&
                yearOfPublish == book.yearOfPublish &&
                name.equals(book.name) &&
                author.equals(book.author) &&
                isbn.equals(book.isbn) &&
                bookStatus == book.bookStatus &&
                Objects.equals(description, book.description) &&
                Objects.equals(arrivalDate, book.arrivalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, isbn, pageNumber, bookStatus, price, orderCount
                , yearOfPublish, description, arrivalDate);
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
