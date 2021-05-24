package com.dao;

import com.api.dao.IRequestDao;
import com.model.Request;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RequestDao extends AbstractDao<Request> implements IRequestDao {

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
                .filter(e -> e.getBook().getId().equals(bookId))
                .findFirst()
                .get();
    }
}
