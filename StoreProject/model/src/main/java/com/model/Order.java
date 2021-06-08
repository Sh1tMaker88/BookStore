package com.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Order extends AIdentity implements Serializable {
    static final long serialVersionUID = 4L;

    @EqualsAndHashCode.Include
    @Column(name = "customer_name")
    private String customerName;

    @JsonIgnoreProperties("orders")
    @JsonIdentityReference(alwaysAsId = true)
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @EqualsAndHashCode.Include
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "date_of_done")
    private LocalDateTime dateOfDone;

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
                ", completed=" + dateOfDone +
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
        return dateOfDone;
    }

    public void setDateOfDone(LocalDateTime dateOfDone) {
        this.dateOfDone = dateOfDone;
    }
}
