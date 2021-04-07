package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order extends AIdentity implements Serializable {

    static final long serialVersionUID = 3L;
    private OrderStatus status = OrderStatus.NEW;
    private List<Book> books;
    private double totalPrice;
    private String customerName;
    private LocalDateTime orderDate;
    private LocalDateTime DateOfDone;

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDateOfDone() {
        return DateOfDone;
    }

    public void setDateOfDone(LocalDateTime dateOfDone) {
        DateOfDone = dateOfDone;
    }

    public Order(String customerName, List<Book> books) {
        this.customerName = customerName;
        this.books = books;
        this.orderDate = LocalDateTime.now();
        totalPrice = totalPrice(books);
    }

    public double totalPrice(List<Book> books){
        double total = 0;
        for (Book book : books){
            total += book.getPrice();
        }
        return total;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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
                status == order.status &&
                Objects.equals(books, order.books) &&
                Objects.equals(customerName, order.customerName) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(DateOfDone, order.DateOfDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, books, totalPrice, customerName, orderDate, DateOfDone);
    }

    @Override
    public String toString() {
        return  "ORDER{" + "orderId=" + getId() +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", customerName='" + customerName + '\'' +
                ", books:\n" + books.toString().replaceAll("^\\[|\\]$", "") +
                "}\n";
    }
}
