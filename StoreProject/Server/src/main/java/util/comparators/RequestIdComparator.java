package util.comparators;

import models.Request;

import java.util.Comparator;

public class RequestIdComparator implements Comparator<Request> {
    @Override
    public int compare(Request o1, Request o2) {
        return o1.getId() - o2.getId();
    }
}
