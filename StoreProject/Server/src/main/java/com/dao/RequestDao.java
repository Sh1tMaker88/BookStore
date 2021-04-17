package com.dao;

import com.annotations.Singleton;
import com.api.dao.IRequestDao;
import com.models.Request;

@Singleton
public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    public RequestDao() {
    }
}
