package com.dao;

import com.annotations.Singleton;
import com.api.dao.IBookDao;
import com.models.Book;

@Singleton
public class BookDao extends AbstractDao<Book> implements IBookDao {

    //todo delete instance
//    private static BookDao instance;

    public BookDao() {

    }

//    public static BookDao getInstance(){
//        if (instance == null) {
//            instance = new BookDao();
//        }
//        return instance;
//    }

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
