package com.util.comparators;

import com.models.Book;

import java.util.Comparator;

public class BookYearOfPublishComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        int result = o1.getYearOfPublish() - (o2.getYearOfPublish());
        if (result == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return result;
    }
}
