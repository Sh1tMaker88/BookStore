package com.api.dao;

import com.model.Request;
import org.springframework.stereotype.Repository;

public interface IRequestDao extends GenericDao<Request> {
    boolean checkIfRequestExistForBookID(Long bookId);

    Request getRequestByBookId(Long bookId);
}
