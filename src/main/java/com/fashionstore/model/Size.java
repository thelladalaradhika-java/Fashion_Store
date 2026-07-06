package com.fashionstore.model;

public class Size {

    private int sizeId;
    private String sizeName;

    // Default Constructor
    public Size() {
    }

    // Parameterized Constructor
    public Size(String sizeName) {
        this.sizeName = sizeName;
    }

    // Getters and Setters

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
