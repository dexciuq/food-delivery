package com.example.delivery.model;

import java.util.Date;
import java.util.List;

public class Order {
    private final Long id;
    private User user;
    private Date date;
    private List<Product> productList;
    private double total;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public double getTotal() {
        return total;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Order(Long id, User user, Date date, List<Product> productList, double total) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.productList = productList;
        this.total = total;
    }
}
