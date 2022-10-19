package com.example.delivery.db;


import com.example.delivery.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends Database {
    private static DatabaseAdapter instance;
    private DatabaseAdapter(){}
    public static synchronized DatabaseAdapter getInstance() {
        if (instance == null) instance = new DatabaseAdapter();
        return instance;
    }
    public List<Product> selectAllProducts() {

        List<Product> allProductsList = new ArrayList<>();
        String sql = "SELECT product_id, product_name, product_description, product_price FROM products";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long productId = rs.getLong(1);
                String productName = rs.getString(2);
                String productDescription = rs.getString(3);
                double productPrice = rs.getDouble(4);

                allProductsList.add(new Product(productId, productName, productDescription, productPrice));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allProductsList;
    }

    public void createUser (String name, String username, String password){
        String sql = "INSERT INTO users (name, username, password) " +
                "VALUES ('" + name + "', '" + username + "', '" + password + "')";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean loginUser(String username, String password){
        String sql = "SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"'";
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                if (username.equals(rs.getString("username"))
                        && password.equals(rs.getString("password"))) return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}