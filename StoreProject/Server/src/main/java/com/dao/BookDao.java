package com.dao;

import com.api.dao.IBookDao;
import com.models.Book;

public class BookDao extends AbstractDao<Book> implements IBookDao {

    private static BookDao instance;

    private BookDao() {

    }

    public static BookDao getInstance(){
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

    @Override
    public Book update(Book entity) {
        Book book = getById(entity.getId());
        book.setName(entity.getName());
        book.setAuthor(entity.getAuthor());
        book.setIsbn(entity.getIsbn());
        book.setPageNumber(entity.getPageNumber());
        book.setBookStatus(entity.getBookStatus());
        book.setPrice(entity.getPrice());
        return book;
    }
}
