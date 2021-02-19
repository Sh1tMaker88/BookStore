package dao;

import api.dao.IBookDao;
import models.Book;

public class BookDao extends AbstractDao<Book> implements IBookDao {
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
