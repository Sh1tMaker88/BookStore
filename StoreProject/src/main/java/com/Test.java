package com;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/senla";
        String user = "bestuser";
        String pass = "bestuser";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement()) {
            System.out.println("--- Connections successful ---");
//            statement.execute();  - statement without return
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pc");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("model"));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
