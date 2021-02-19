package models;

import java.time.LocalDate;

public class Book extends AIdentity {

    private String name;
    private String author;
    private String isbn;
    private int pageNumber;
    private BookStatus bookStatus = BookStatus.IN_STOCK;
    private double price;
    private int orderCount = 0;
    private int yearOfPublish;
    private String description = "";
    private LocalDate arrivalDate;

    public Book(String name, String author, int yearOfPublish, double price, String isbn, int pageNumber) {
        this.name = name;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
        this.price = price;
        this.isbn = isbn;
        this.pageNumber = pageNumber;
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

    @Override
    public String toString() {
        return "Book{" + super.toString() +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", bookStatus=" + bookStatus +
                ", price=" + price +
                "} \n";
    }
}
