package com.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "arrival_date")
    private LocalDate arrivalDate = LocalDate.now();


    @Column(name = "order_count")
    private int orderCount = 0;

    @JsonBackReference(value = "request")
    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    private Request request;

//    @JsonIgnore
    @JsonIgnoreProperties("books")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}
            , fetch = FetchType.LAZY)
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

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
