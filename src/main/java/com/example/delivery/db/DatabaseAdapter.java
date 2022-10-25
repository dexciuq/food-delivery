package com.example.delivery.db;


import com.example.delivery.model.Product;
import com.example.delivery.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseAdapter extends Database {
    private static DatabaseAdapter instance;
    private DatabaseAdapter(){}
    public static synchronized DatabaseAdapter getInstance() {
        if (instance == null) instance = new DatabaseAdapter();
        return instance;
    }
    public List<Product> selectAllProducts() {
        List<Product> allProductsList = new ArrayList<>();
        String sql = "SELECT product_id, product_name, product_description, product_price, product_src FROM products";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long productId = rs.getLong(1);
                String productName = rs.getString(2);
                String productDescription = rs.getString(3);
                double productPrice = rs.getDouble(4);
                String productSrc = rs.getString(5);

                allProductsList.add(new Product(productId, productName, productDescription, productPrice, productSrc));
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
                        && password.equals(rs.getString("password"))) {
                    User user = User.getInstance();
                    user.setId(Long.valueOf(rs.getString("user_id")));
                    user.setName(rs.getString("name"));
                    user.setUsername(username);
                    user.setPassword(password);
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void createOrder (String date, List<Product> products, double total){

        StringBuilder productIdList = new StringBuilder();
        for (Product product: products) {
            productIdList.append(product.getProductId()).append(" ");
        }
        String sql = "INSERT INTO orders (user_id, date , products, total) " +
                "VALUES ('" + User.getInstance().getId() + "', '" + date + "', '" + productIdList + "', '" + total + "')";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}