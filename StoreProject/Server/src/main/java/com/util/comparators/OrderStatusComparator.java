package com.util.comparators;

import com.models.Order;

import java.util.Comparator;

public class OrderStatusComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        int result = o1.getStatus().compareTo(o2.getStatus());
        if (result == 0) {
            return o1.getCustomerName().compareTo(o2.getCustomerName());
        }
        return result;
    }
}
