package com.fashionstore.model;

public class ProductSize {

    private int id;
    private int productId;
    private int sizeId;
    private int stockQuantity;

    // Default Constructor
    public ProductSize() {
    }

    // Parameterized Constructor
    public ProductSize(int productId, int sizeId, int stockQuantity) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}