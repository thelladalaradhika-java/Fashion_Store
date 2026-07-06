package com.fashionstore.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.fashionstore.util.DBConnection;
import com.fashionstore.model.User;
import com.fashionstore.controller.CartServlet.CartItem;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object sessionUserObj = session.getAttribute("currentUser");
        String searchKey = "";

        if (sessionUserObj == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int loggedInUserId = 0;
        if (sessionUserObj instanceof User) {
            searchKey = ((User) sessionUserObj).getEmail(); 
            loggedInUserId = ((User) sessionUserObj).getUserId();
        } else {
            searchKey = sessionUserObj.toString();
        }
        
        String dbName = ""; 
        String dbPhone = "";
        String dbAddress = "";
        String dbCity = "";
        String dbState = "";
        String dbPincode = "";
        String dbCountry = "";

        String userQuery = "SELECT user_id, name, phone, address, city, state, pincode, country FROM users WHERE email = ? OR name = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(userQuery)) {
            
            ps.setString(1, searchKey);
            ps.setString(2, searchKey);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (loggedInUserId == 0) {
                        loggedInUserId = rs.getInt("user_id");
                    }
                    dbName = rs.getString("name");
                    dbPhone = rs.getString("phone");
                    dbAddress = rs.getString("address");
                    dbCity = rs.getString("city");
                    dbState = rs.getString("state");
                    dbPincode = rs.getString("pincode");
                    dbCountry = rs.getString("country");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        session.setAttribute("resolvedUserId", loggedInUserId);
        request.setAttribute("dbName", (dbName != null && !dbName.isEmpty()) ? dbName : searchKey);
        request.setAttribute("dbPhone", dbPhone != null ? dbPhone : "");
        request.setAttribute("dbAddress", dbAddress != null ? dbAddress : "");
        request.setAttribute("dbCity", dbCity != null ? dbCity : "");
        request.setAttribute("dbState", dbState != null ? dbState : "");
        request.setAttribute("dbPincode", dbPincode != null ? dbPincode : "");
        request.setAttribute("dbCountry", dbCountry != null ? dbCountry : "");
        
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object sessionUserObj = session.getAttribute("currentUser");
        
        if (sessionUserObj == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<CartItem> activeCart = (List<CartItem>) session.getAttribute("cartList");

        // Extracting form input variables
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");
        String country = request.getParameter("country");
        String paymentMethod = request.getParameter("paymentMethod");

        // 🌟 FIX 1: Calculate the exact mathematical total directly from the active session cart list 
        // to prevent it from ever showing up as 0.00
        double computedSubtotal = 0.0;
        if (activeCart != null) {
            for (CartItem item : activeCart) {
                computedSubtotal += item.getPrice() * item.getQuantity();
            }
        }
        double deliveryFee = computedSubtotal > 0 ? 99.0 : 0.0;
        double calculatedTotalCharges = computedSubtotal + deliveryFee;

        // Fetch User ID
        Integer userIdObj = (Integer) session.getAttribute("resolvedUserId");
        int userId = (userIdObj != null) ? userIdObj : 1; 

        // Target insert query matching your exact database columns
        String insertOrderSQL = "INSERT INTO orders (user_id, total_amount, status, payment_method, payment_status, address, pincode, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int generatedOrderId = 0;
        java.sql.Timestamp databaseOrderTimestamp = new java.sql.Timestamp(System.currentTimeMillis());

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, userId);
            ps.setDouble(2, calculatedTotalCharges); // Saves computed total directly into your decimal(10,2) database field
            ps.setString(3, "PLACED");
            ps.setString(4, paymentMethod != null ? paymentMethod : "COD");
            ps.setString(5, "PENDING");
            ps.setString(6, address);
            ps.setString(7, pincode);
            ps.setString(8, phone);

            ps.executeUpdate();
            
            // 🌟 FIX 2: Extract the genuine, live auto-increment ID directly generated by your database table record
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedOrderId = generatedKeys.getInt(1);
                }
            }

            // Bind values directly to request attributes for order-confirmation.jsp
            request.setAttribute("orderId", generatedOrderId); // This is your live database order ID
            request.setAttribute("orderDate", databaseOrderTimestamp.toString()); // Database timestamp instance
            request.setAttribute("paymentMethod", paymentMethod != null ? paymentMethod : "COD");
            request.setAttribute("totalCharges", String.format("%.2f", calculatedTotalCharges)); // Accurate total cost string
            
            // User interface textual details mapping
            request.setAttribute("name", fullName);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.setAttribute("city", city);
            request.setAttribute("state", state);
            request.setAttribute("pincode", pincode);
            request.setAttribute("country", country);

            request.setAttribute("confirmedItemsList", activeCart);

            request.getRequestDispatcher("/WEB-INF/views/order-confirmation.jsp").forward(request, response);
            
            // Clear session cart state after rendering is completed safely
            session.removeAttribute("cartList"); 
            return;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Order placement failed: " + e.getMessage());
        }
    }
}