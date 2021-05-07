package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "book")
//@DynamicUpdate
public class Book extends AIdentity implements Serializable {

    static final long serialVersionUID = 4L;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_year")
    private int yearOfPublish;

    @Column(name = "page_number")
    private int pageNumber;

    @EqualsAndHashCode.Include
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "price")
    private double price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @Column(name = "description")
    private String description = "";

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "order_count")
    private int orderCount = 0;

    @OneToOne(mappedBy = "book")
    private Request request;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}
            , fetch = FetchType.EAGER)
    @JoinTable(name = "ordering_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
    private List<Order> orders;

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

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
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
