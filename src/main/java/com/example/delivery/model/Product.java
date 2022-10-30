package com.example.delivery.model;
public class Product{
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String productSrc;

    public Product(Long productId, String productName, String productDescription, double productPrice, String productSrc) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productSrc = productSrc;
    }

    public String getProductSrc() {
        return productSrc;
    }
    public void setProductSrc(String productSrc) {
        this.productSrc = productSrc;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return  getProductName().toUpperCase()
                + "\nPrice: " + getProductPrice() + "$"
                + "\n" + getProductDescription();
    }

    public String toStringForOrder() {
        return  "\n" +getProductName().toUpperCase()
                + "\tPrice: " + getProductPrice() + "$";
    }
}

