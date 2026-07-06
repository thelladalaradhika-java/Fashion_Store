package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.CartItem;

public interface CartItemDAO {

    // 🆕 Add item to cart
    boolean addCartItem(CartItem item);

    // 🔍 Get items by user/cart
    List<CartItem> getCartItemsByUser(int userId);

    // ✏️ Update quantity
    boolean updateQuantity(int cartItemId, int quantity);

    // ❌ Remove item
    boolean removeCartItem(int cartItemId);

    // ❌ Clear cart items
    boolean clearCart(int userId);
}

