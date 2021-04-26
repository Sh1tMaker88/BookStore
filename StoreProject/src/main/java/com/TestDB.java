package com;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TestDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/senla";
//                "?useUnicode=true" +
//                "&useJDBCCompliantTimezoneShift=true" +
//                "&useLegacyDatetimeCode=false" +
//                "&serverTimezone=Europe/Moscow";
        String user = "bestuser";
        String pass = "bestuser";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement()) {
            System.out.println("--- Connections successful ---");
//            statement.execute();  - statement without return

            ResultSet resultSet = statement.executeQuery("SELECT * FROM pc");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("model") + ", ram=" + resultSet.getInt("ram"));
            }
            resultSet.close();

//            PreparedStatement statement1 = connection.prepareStatement("SELECT ? FROM ?");
//            String s1 = "model";
//            String s2 = "laptop";
//            statement1.setString(1, s1);
//            statement1.setString(2, s2);
//            ResultSet resultSet1 = statement1.executeQuery();
//            while (resultSet1.next()) {
//                System.out.println(resultSet1.getString("model"));
//            }


            Statement statement2 = connection.createStatement();
            String sql = "SELECT model FROM pc WHERE code=55";
            ResultSet resultSet1 = statement2.executeQuery(sql);
            System.out.println();
            System.out.println(resultSet1.next());
//            String i = resultSet1.getString("model");
//            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
