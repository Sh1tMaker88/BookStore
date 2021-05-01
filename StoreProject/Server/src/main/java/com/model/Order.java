package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
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
