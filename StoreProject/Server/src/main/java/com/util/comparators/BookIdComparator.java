package com.util.comparators;

import com.models.Book;

import java.util.Comparator;

public class BookIdComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return Long.compare(o1.getId(), o2.getId());
    }


}
