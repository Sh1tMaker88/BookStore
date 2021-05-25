package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ordering")
public class Order extends AIdentity implements Serializable {
    static final long serialVersionUID = 4L;

    @EqualsAndHashCode.Include
    @Column(name = "customer_name")
    private String customerName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}
            , fetch = FetchType.LAZY)
    @JoinTable(name = "ordering_book",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private List<Book> books;

    @EqualsAndHashCode.Include
    @Column(name = "price")
    private double totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    @EqualsAndHashCode.Include
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "date_of_done")
    private LocalDateTime DateOfDone;

    public Order() {

    }

    public Order(String customerName, List<Book> books) {
        this.customerName = customerName;
        this.books = books;
        this.orderDate = LocalDateTime.now();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    @Override
    public String toString() {
        return "ORDER{" + "orderId=" + getId() +
                ", customerName='" + customerName + '\'' +
                ", booksID:" + books.stream().map(AIdentity::getId).collect(Collectors.toList()) +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", ordered=" + orderDate +
                ", completed=" + DateOfDone +
                "}\n";
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDateOfDone() {
        return DateOfDone;
    }

    public void setDateOfDone(LocalDateTime dateOfDone) {
        DateOfDone = dateOfDone;
    }
}
