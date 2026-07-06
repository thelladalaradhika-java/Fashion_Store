package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    @Override
    public boolean addToCart(Cart cart) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO cart (user_id) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cart.getUserId());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<Cart> getCartByUser(int userId) {
        List<Cart> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM cart WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();

                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));

                list.add(cart);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateQuantity(int cartId, int quantity) {
        // ❌ NOT APPLICABLE (quantity is in cart_items table)
        return false;
    }

    @Override
    public boolean removeFromCart(int cartId) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM cart WHERE cart_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cartId);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean clearCart(int userId) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM cart WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}