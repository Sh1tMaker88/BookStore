package com.api.dao;

import com.model.Request;

public interface IRequestDao extends GenericDao<Request> {
    boolean checkIfRequestExist(Long bookId);

    Request getRequestByBookId(Long bookId);
}
