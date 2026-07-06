//package com.fashionstore.controller;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.sql.*;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/home")
//public class HomeServlet extends HttpServlet {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//    private static final String DB_USER = "root";
//    private static final String DB_PASS = "Radhikamohan@02";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        List<HomeProduct> featuredList = new ArrayList<>();
//        
//        System.out.println("=== HOMESERVLET: STARTING DATABASE FETCH ===");
//        
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
//                System.out.println("✅ HOMESERVLET: Connected to Database successfully.");
//                
//                DatabaseMetaData dbm = conn.getMetaData();
//                try (ResultSet tables = dbm.getTables(null, null, "products", null)) {
//                    if (!tables.next()) {
//                        System.out.println("❌ ERROR: 'products' table does NOT exist in schema 'fashion_store'!");
//                    }
//                }
//
//                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM products LIMIT 4");
//                     ResultSet rs = ps.executeQuery()) {
//                    
//                    int rowCounter = 0;
//                    while (rs.next()) {
//                        rowCounter++;
//                        
//                        // Smart check: read column by index or fall back safely
//                        int id = 1;
//                        try { id = rs.getInt("product_id"); } catch(Exception e) { id = rs.getInt(1); }
//                        
//                        String title = "Unknown Item";
//                        try {
//                            title = rs.getString("name");
//                        } catch (Exception e) {
//                            try {
//                                title = rs.getString("title");
//                            } catch (Exception ex) {
//                                title = rs.getString(2);
//                            }
//                        }
//                        
//                        String brand = "Exclusive Line";
//                        try { brand = rs.getString("brand"); } catch(Exception e){}
//                        
//                        double price = 999.0;
//                        try { price = rs.getDouble("price"); } catch(Exception e){}
//
//                        // 🌟 ADDED IMAGE EXTRACTION: Fetch image_url value dynamically from MySQL
//                        String imageUrl = "img/shirt.jpg";
//                        try {
//                            imageUrl = rs.getString("image_url");
//                        } catch(Exception e){}
//                        if(imageUrl == null || imageUrl.trim().isEmpty()) {
//                            if(id == 2) imageUrl = "img/jeans.jpg";
//                            else if(id == 3) imageUrl = "img/polo.jpg";
//                            else if(id == 4) imageUrl = "img/dress.jpg";
//                            else imageUrl = "img/shirt.jpg";
//                        }
//
//                        System.out.println("📦 Found Product Row #" + rowCounter + ": ID=" + id + ", Title=" + title + ", Image=" + imageUrl);
//                        featuredList.add(new HomeProduct(id, title, brand, price, imageUrl));
//                    }
//                    
//                    System.out.println("📊 Total rows loaded into list: " + featuredList.size());
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("❌ HOMESERVLET CRITICAL EXCEPTION ENCOUNTERED:");
//            e.printStackTrace();
//        }
//        
//        if (featuredList.isEmpty()) {
//            System.out.println("⚠️ WARNING: Database returned 0 rows. Loading hardcoded backup items instead.");
//            featuredList.add(new HomeProduct(1, "Black Casual Shirt", "Exclusive Line", 1299.0, "img/shirt.jpg"));
//            featuredList.add(new HomeProduct(2, "Blue Slim Fit Jeans", "Exclusive Line", 1999.0, "img/jeans.jpg"));
//            featuredList.add(new HomeProduct(3, "White Polo T-Shirt", "Exclusive Line", 999.0, "img/polo.jpg"));
//            featuredList.add(new HomeProduct(4, "Floral Summer Dress", "Exclusive Line", 2499.0, "img/dress.jpg"));
//        }
//        
//        request.setAttribute("featuredItems", featuredList);
//        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
//    }
//
//    public static class HomeProduct {
//        private final int id;
//        private final String title;
//        private final String brand;
//        private final double price;
//        private final String imageUrl; // 🌟 TRACK IMAGE PARAMETER
//
//        public HomeProduct(int id, String title, String brand, double price, String imageUrl) {
//            this.id = id;
//            this.title = title;
//            this.brand = brand;
//            this.price = price;
//            this.imageUrl = imageUrl;
//        }
//
//        public int getId() { return id; }
//        public String getTitle() { return title; }
//        public String getBrand() { return brand; }
//        public double getPrice() { return price; }
//        public String getImageUrl() { return imageUrl; } // 🌟 IMAGE GETTER METHOD
//    }
//}







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