package com.dao;

import com.annotations.Singleton;
import com.api.dao.IRequestDao;
import com.model.Request;

@Singleton
public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    public RequestDao() {
    }

    @Override
    protected Request updateEntityFields(Request entityToUpdate, Request entity) {
        entityToUpdate.setBook(entity.getBook());
        entityToUpdate.setRequestDate(entity.getRequestDate());
        entityToUpdate.setRequestCount(entity.getRequestCount());
        entityToUpdate.setRequestStatus(entity.getRequestStatus());
        return entityToUpdate;
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
