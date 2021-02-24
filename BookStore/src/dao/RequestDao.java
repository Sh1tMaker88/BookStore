package dao;

import api.dao.IRequestDao;
import models.Request;

import java.util.Objects;

public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    private static RequestDao instance;

    private RequestDao() {
    }

    public static RequestDao getInstance(){
        return Objects.requireNonNullElse(instance, new RequestDao());
    }
}
