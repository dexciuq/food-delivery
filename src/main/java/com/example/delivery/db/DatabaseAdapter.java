package com.example.delivery.db;


import com.example.delivery.model.Order;
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

    public Product selectProductById(int id) {
        Product product = null;
        String sql = "SELECT product_id, product_name, product_description, product_price, product_src " +
                "FROM products " +
                "WHERE product_id = " + id;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long productId = rs.getLong(1);
                String productName = rs.getString(2);
                String productDescription = rs.getString(3);
                double productPrice = rs.getDouble(4);
                String productSrc = rs.getString(5);
                product = new Product(productId, productName, productDescription, productPrice, productSrc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
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

    public List<Order> selectAllOrdersById(Long id) {
        List<Order> allOrderList = new ArrayList<>();
        String sql = "SELECT order_id, date, products, total FROM orders WHERE user_id = " + id;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long orderId = rs.getLong(1);
                String orderDate = rs.getString(2);
                String orderProductsString = rs.getString(3);
                double orderTotal = rs.getDouble(4);
                allOrderList.add(new Order(orderId, id, orderDate, getOrderListFromString(orderProductsString), orderTotal));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allOrderList;
    }

    private List<Product> getOrderListFromString(String orderProductsString) {
        List<Product> orderProducts = new ArrayList<>();
        String[] products = orderProductsString.trim().split("\\s+");
        for (String product: products) {
            orderProducts.add(selectProductById(Integer.parseInt(product)));
        }
        return orderProducts;
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