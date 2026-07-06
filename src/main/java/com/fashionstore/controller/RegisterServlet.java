package com.fashionstore.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Radhikamohan@02";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String addressLine1 = request.getParameter("addressLine1");
        
        // 🌟 EXTRACT ALL INDIVIDUAL GEOGRAPHICAL VALUES FROM FORM
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");
        String country = request.getParameter("country"); // Make sure your register.jsp has name="country"
        String role = request.getParameter("role");       // Grabs 'Customer', 'Admin', or 'Delivery Boy'

        // Default to Customer if role dropdown value is missing
        if (role == null || role.trim().isEmpty()) {
            role = "Customer";
        }

        // 🌟 UPDATED SQL: Inserts all individual columns explicitly into the database row
        String insertSQL = "INSERT INTO users (name, email, password, phone, address, city, state, pincode, country, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                
                ps.setString(1, fullName);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, phone);
                ps.setString(5, addressLine1); // Saves street address cleanly 
                ps.setString(6, city);         // 🌟 Saves City to column
                ps.setString(7, state);        // 🌟 Saves State to column
                ps.setString(8, pincode);      // 🌟 Saves Pincode to column
                ps.setString(9, country);      // 🌟 Saves Country to column
                ps.setString(10, role);        // 🌟 Saves Account Type Role to column

                if (ps.executeUpdate() > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("successMessage", "Registration Successful! Please Sign In.");
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
}