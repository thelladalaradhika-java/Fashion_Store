package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Order;

public interface OrderDAO {
    
    boolean createOrder(Order order);
    
    Order getOrderById(int orderId);
    
    List<Order> getOrdersByUserId(int userId);
    
    List<Order> getAllOrders();
    
    boolean updateOrderStatus(int orderId, String status);
}
