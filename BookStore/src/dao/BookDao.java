package dao;

import api.dao.IBookDao;
import models.Book;

import java.util.Objects;

public class BookDao extends AbstractDao<Book> implements IBookDao {

    private static BookDao instance;

    private BookDao() {

    }

    public static BookDao getInstance(){
        return Objects.requireNonNullElse(instance, new BookDao());
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
