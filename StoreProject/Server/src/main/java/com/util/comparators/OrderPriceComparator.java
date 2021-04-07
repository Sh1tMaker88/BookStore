package com.util.comparators;

import com.models.Order;

import java.util.Comparator;

public class OrderPriceComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return (int)(o1.getTotalPrice() - o2.getTotalPrice());
    }
}
