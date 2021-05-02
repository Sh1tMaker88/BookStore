package com.dao.util;

import com.annotations.ClassToInjectProperty;
import com.annotations.InjectValueFromProperties;
import com.annotations.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@ClassToInjectProperty
public class Connector {

    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());
    @InjectValueFromProperties
    private static String NAME;
    @InjectValueFromProperties
    private static String PASSWORD;
    @InjectValueFromProperties
    private static String URL;
    private Connection connection;

    public Connector() {
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDB();
            }
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void connectToDB() {
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
