package com.dao;

import com.annotations.Singleton;
import com.api.dao.IRequestDao;
import com.models.Request;

@Singleton
public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    //todo delete instance
//    private static RequestDao instance;

    public RequestDao() {
    }

//    public static RequestDao getInstance(){
//        if (instance == null) {
//            instance = new RequestDao();
//        }
//        return instance;
//    }
}
