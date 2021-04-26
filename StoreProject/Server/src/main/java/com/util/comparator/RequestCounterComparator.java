package com.util.comparator;

import com.model.Request;

import java.util.Comparator;

public class RequestCounterComparator implements Comparator<Request> {

    //todo fix it
    @Override
    public int compare(Request o1, Request o2) {
        return Integer.compare(o1.getRequestCount(), o2.getRequestCount());
    }
}
