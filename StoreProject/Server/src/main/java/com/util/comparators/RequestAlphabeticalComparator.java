package com.util.comparators;

import com.models.Request;

import java.util.Comparator;

public class RequestAlphabeticalComparator implements Comparator<Request> {

    @Override
    public int compare(Request o1, Request o2) {
        return o1.getBook().getName().compareTo(o2.getBook().getName());
    }
}
