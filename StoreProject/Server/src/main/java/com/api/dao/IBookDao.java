package com.api.dao;

import com.model.Book;


public interface IBookDao extends GenericDao<Book> {
    public void updateOrderCount(Book book);
}
