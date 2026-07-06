package com.fashionstore.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fashionstore.util.DBConnection;

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/product");
            return;
        }

        // Target matching attributes placeholders initialization
        String orderDate = "";
        String paymentMethod = "COD";
        String orderStatus = "PLACED";
        String totalAmount = "0.00";
        String fullName = "";
        String phone = "";
        String addressLine = "";
        String cityStateZip = "";
        String country = "";

        // Query string lookup targeting orders table row values
        String query = "SELECT order_date, payment_method, full_name, phone, address, city, state, pincode, country " +
                       "FROM orders WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, Integer.parseInt(orderIdParam));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderDate = rs.getTimestamp("order_date") != null ? rs.getTimestamp("order_date").toString() : "";
                    paymentMethod = rs.getString("payment_method");
                    fullName = rs.getString("full_name");
                    phone = rs.getString("phone");
                    addressLine = rs.getString("address");
                    cityStateZip = rs.getString("city") + ", " + rs.getString("state") + " - " + rs.getString("pincode");
                    country = rs.getString("country");
                    
                    // Mock processing or pull dynamically if your database schema stores total amount here
                    totalAmount = "8195.00"; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set attributes dynamically so the JSP can read them safely
        request.setAttribute("orderId", orderIdParam);
        request.setAttribute("orderDate", orderDate);
        request.setAttribute("paymentMethod", paymentMethod != null ? paymentMethod : "COD");
        request.setAttribute("orderStatus", orderStatus);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("fullName", fullName);
        request.setAttribute("phone", phone);
        request.setAttribute("addressLine", addressLine);
        request.setAttribute("cityStateZip", cityStateZip);
        request.setAttribute("country", country != null ? country : "India");

        request.getRequestDispatcher("/WEB-INF/views/order-confirmation.jsp").forward(request, response);
    }
}