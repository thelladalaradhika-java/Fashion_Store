package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Radhikamohan@02";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<HomeProduct> featuredList = new ArrayList<>();
        
        System.out.println("=== HOMESERVLET: STARTING DATABASE FETCH ===");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                System.out.println("✅ HOMESERVLET: Connected to Database successfully.");

                // 🔄 FIXED: Removed "LIMIT 4" so all 24 products load!
                String sql = "SELECT * FROM products";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    
                    int rowCounter = 0;
                    while (rs.next()) {
                        rowCounter++;
                        
                        int id = rs.getInt("product_id");
                        
                        String title = "Unknown Item";
                        try { title = rs.getString("name"); } catch (Exception e) { title = rs.getString("title"); }
                        
                        String brand = "Exclusive Line";
                        try { brand = rs.getString("brand"); } catch(Exception e){}
                        
                        double price = 999.0;
                        try { price = rs.getDouble("price"); } catch(Exception e){}

                        String imageUrl = "img/shirt.jpg";
                        try { imageUrl = rs.getString("image_url"); } catch(Exception e){}

                        System.out.println("📦 Found Product Row #" + rowCounter + ": ID=" + id + ", Title=" + title + ", Image=" + imageUrl);
                        featuredList.add(new HomeProduct(id, title, brand, price, imageUrl));
                    }
                    System.out.println("📊 Total rows loaded into list: " + featuredList.size());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ HOMESERVLET CRITICAL EXCEPTION:");
            e.printStackTrace();
        }
        
        request.setAttribute("featuredItems", featuredList);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }

    public static class HomeProduct {
        private final int id;
        private final String title;
        private final String brand;
        private final double price;
        private final String imageUrl;

        public HomeProduct(int id, String title, String brand, double price, String imageUrl) {
            this.id = id;
            this.title = title;
            this.brand = brand;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public String getBrand() { return brand; }
        public double getPrice() { return price; }
        public String getImageUrl() { return imageUrl; }
    }
}