package com.dao;

import com.annotations.Singleton;
import com.api.dao.IRequestDao;
import com.models.Request;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
public class RequestDao extends AbstractDao<Request> implements IRequestDao {

    public RequestDao() {
    }


    //todo imp methods
    @Override
    protected String getInsertQuery() {
        return null;
    }

    @Override
    protected String getGetAllQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getCountOfObjectsQuery() {
        return null;
    }

    @Override
    protected void preparedStatementForCreate(PreparedStatement statement, Request entity) throws SQLException {

    }

    @Override
    protected void preparedStatementForUpdate(PreparedStatement statement, Request entity) throws SQLException {

    }

    @Override
    protected String getTableName() {
        return null;
    }
}
