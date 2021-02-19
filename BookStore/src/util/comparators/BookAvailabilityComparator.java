package util.comparators;

import models.Book;

import java.util.Comparator;

public class BookAvailabilityComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        int result = o1.getBookStatus().compareTo(o2.getBookStatus());
        if (result == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return result;
    }
}
