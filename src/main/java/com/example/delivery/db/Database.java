package com.example.delivery.db;

import java.sql.*;

class Database {
    private String jdbcURL = "jdbc:postgresql://localhost:5432/slice";
    private String username = "postgres";
    private String password = "0000";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public void connect() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
            if (connection != null) connection.close();
            System.out.println("Database disconnected successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}