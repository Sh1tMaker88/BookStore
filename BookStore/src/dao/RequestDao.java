package dao;

import api.dao.IRequestDao;
import models.Request;
import service.BookService;


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
