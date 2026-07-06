package com.fashionstore.model;

import java.sql.Timestamp;

public class Product {

    private int productId;
    private String name;
    private String description;
    private double price;
    private int categoryId;
    private int stock;
    private String brand;
    private String imageUrl;
    private Timestamp createdAt;

    // ✅ Default Constructor
    public Product() {
    }

    // ✅ Constructor for INSERT (while adding product)
    public Product(String name, String description, double price,
                   int categoryId, int stock, String brand, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.stock = stock;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }

    // ✅ Constructor for FETCH (from DB)
    public Product(int productId, String name, String description, double price,
                   int categoryId, int stock, String brand, String imageUrl, Timestamp createdAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.stock = stock;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    // ✅ Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}