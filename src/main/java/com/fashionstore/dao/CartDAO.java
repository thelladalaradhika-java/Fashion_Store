package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Cart;

public interface CartDAO {

    // 🆕 Add to Cart
    boolean addToCart(Cart cart);

    // 🔍 Get Methods
    List<Cart> getCartByUser(int userId);

    // ✏️ Update
    boolean updateQuantity(int cartId, int quantity);

    // ❌ Remove
    boolean removeFromCart(int cartId);

    // ❌ Clear Cart
    boolean clearCart(int userId);
}