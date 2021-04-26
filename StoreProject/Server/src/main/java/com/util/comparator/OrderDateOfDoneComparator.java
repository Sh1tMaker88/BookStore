package com.util.comparator;

import com.model.Order;

import java.util.Comparator;

public class OrderDateOfDoneComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        return o1.getOrderDate().compareTo(o2.getOrderDate());
    }
}
