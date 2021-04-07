package com.dao;

import com.api.dao.IRequestDao;
import com.models.Request;


public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    private static RequestDao instance;

    private RequestDao() {
    }

    public static RequestDao getInstance(){
        if (instance == null) {
            instance = new RequestDao();
        }
        return instance;
    }
}
