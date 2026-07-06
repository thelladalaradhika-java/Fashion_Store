package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.OrderItem;

public interface OrderItemDAO {

    // 🆕 Add item to order
    boolean addOrderItem(OrderItem item);

    // 🔍 Get items by order
    List<OrderItem> getItemsByOrderId(int orderId);

    // ❌ Delete item
    boolean deleteOrderItem(int itemId);
}