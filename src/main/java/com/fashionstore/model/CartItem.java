package com.fashionstore.model;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int productId;
    private int sizeId;
    private int quantity;

    // Default Constructor
    public CartItem() {
    }

    // Parameterized Constructor
    public CartItem(int cartId, int productId, int sizeId, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    // Getters and Setters

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
