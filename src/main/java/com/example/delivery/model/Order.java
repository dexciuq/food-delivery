package com.example.delivery.model;

import java.util.Date;
import java.util.List;

public class Order {
    private Long orderId;
    private Long orderUserId;
    private String orderDate;
    private List<Product> orderProducts;
    private double orderTotal;

    public Order(Long orderId, Long orderUserId, String orderDate, List<Product> orderProducts, double orderTotal) {
        this.orderId = orderId;
        this.orderUserId = orderUserId;
        this.orderDate = orderDate;
        this.orderProducts = orderProducts;
        this.orderTotal = orderTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Long orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    private String getProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product: orderProducts) {
            stringBuilder.append(product.toStringForOrder());
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Order id: " + orderId + "\n" +
                "Date: " + orderDate + "\n" +
                "Products: " + getProducts() + "\n" +
                "Total: " + orderTotal + "\n";
    }
}
