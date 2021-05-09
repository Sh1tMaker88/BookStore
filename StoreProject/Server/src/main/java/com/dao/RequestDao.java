package com.dao;

import com.api.dao.IRequestDao;
import com.model.Request;
import org.springframework.stereotype.Repository;

@Repository
public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    public RequestDao() {
    }

    @Override
    protected String getClassName() {
        return "Request";
    }

    @Override
    protected Class<Request> getClazz() {
        return Request.class;
    }

    @Override
    public boolean checkIfRequestExist(Long bookId) {
        return getAll().stream()
                .anyMatch(e -> e.getBook()
                        .getId()
                        .equals(bookId));
    }

    @Override
    public Request getRequestByBookId(Long bookId) {
        return getAll().stream()
                .findFirst()
                .filter(e -> e.getBook().getId().equals(bookId))
                .get();
    }
}
