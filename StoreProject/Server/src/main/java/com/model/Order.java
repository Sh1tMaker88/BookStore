package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Order extends AIdentity implements Serializable {

    static final long serialVersionUID = 3L;
    private String customerName;
    private List<Long> booksId = new ArrayList<>();
    private double totalPrice;
    private OrderStatus status = OrderStatus.NEW;
    private LocalDateTime orderDate;
    private LocalDateTime DateOfDone;

    public Order() {

    }

    public Order(String customerName, List<Long> bookId) {
        this.customerName = customerName;
        this.booksId = bookId;
        this.orderDate = LocalDateTime.now();
    }

    public void addBookId(Long id) {
        this.booksId.add(id);
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = LocalDateTime.parse(orderDate);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Long> getBooksId() {
        return booksId;
    }

    public void setBooks(List<Long> books_id) {
        this.booksId = books_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.totalPrice, totalPrice) == 0 &&
                customerName.equals(order.customerName) &&
                Objects.equals(booksId, order.booksId) &&
                status == order.status &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(DateOfDone, order.DateOfDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, booksId, totalPrice, status, orderDate, DateOfDone);
    }

    @Override
    public String toString() {
        return  "ORDER{" + "orderId=" + getId() +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", customerName='" + customerName + '\'' +
                ", books:" + booksId +
                "}\n";
    }
}
