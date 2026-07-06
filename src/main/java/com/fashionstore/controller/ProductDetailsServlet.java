package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/product/details")
public class ProductDetailsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Radhikamohan@02";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Parse incoming productId parameter directly
        String idParam = request.getParameter("productId");
        int productId = -1; 
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                productId = Integer.parseInt(idParam.trim());
            } catch (NumberFormatException e) {
                productId = -1;
            }
        }

        // If no valid ID is passed, immediately return to avoid loading bad data
        if (productId == -1) {
            response.sendRedirect(request.getContextPath() + "/product");
            return;
        }

        DetailedProduct product = null;
        List<DetailedProduct> similarList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                 
                 // 2. Fetch the SELECTED item using database values only
                 String sql = "SELECT * FROM products WHERE product_id = ?";
                 try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, productId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int id = rs.getInt("product_id");
                            String name = rs.getString("name");
                            
                            String brand = rs.getString("brand");
                            if (brand == null || brand.trim().isEmpty()) brand = "Exclusive";
                            
                            double price = rs.getDouble("price");

                            // Dynamic check to pull category values safely regardless of schema columns
                            String category = null;
                            try { category = rs.getString("category"); } catch(Exception e){}
                            if (category == null || category.trim().isEmpty()) {
                                try { category = rs.getString("category_name"); } catch(Exception e){}
                            }
                            if (category == null || category.trim().isEmpty()) {
                                try {
                                    int catId = rs.getInt("category_id");
                                    if(catId == 1) category = "Men";
                                    else if(catId == 2) category = "Women";
                                    else if(catId == 3) category = "Kids";
                                    else if(catId == 4) category = "Accessories";
                                } catch(Exception e){}
                            }
                            if (category == null) category = "General";

                            // Dynamic image string selection
                            String img = null;
                            try { img = rs.getString("image_url"); } catch(Exception e){}
                            if (img == null || img.trim().isEmpty()) {
                                try { img = rs.getString("image"); } catch(Exception e){}
                            }
                            
                            // Keep dynamic file path prefix accurate to your structure
                            if (img != null && !img.trim().isEmpty() && !img.contains("/")) {
                                img = "img/" + img;
                            }

                            String description = rs.getString("description");
                            if (description == null || description.trim().isEmpty()) {
                                description = "Premium quality product selected from our catalog collection.";
                            }

                            product = new DetailedProduct(id, name, brand, price, category, img, description);
                        }
                    }
                 }

                 // 3. Query SIMILAR ITEMS from database belonging strictly to the same category
                 if (product != null) {
                    // Uses case-insensitive matching to ensure accurate categories (Men, Women, Kids, Accessories)
                    String similarSql = "SELECT * FROM products WHERE (LOWER(category) = LOWER(?) OR LOWER(category_name) = LOWER(?)) AND product_id != ? LIMIT 4";
                    try (PreparedStatement psSim = conn.prepareStatement(similarSql)) {
                        psSim.setString(1, product.getCategory());
                        psSim.setString(2, product.getCategory());
                        psSim.setInt(3, product.getId());
                        
                        try (ResultSet rsSim = psSim.executeQuery()) {
                            while (rsSim.next()) {
                                int simId = rsSim.getInt("product_id");
                                String simName = rsSim.getString("name");
                                
                                String simBrand = rsSim.getString("brand");
                                if (simBrand == null || simBrand.trim().isEmpty()) simBrand = "Exclusive Line";
                                
                                double simPrice = rsSim.getDouble("price");
                                String simCat = product.getCategory();
                                
                                String simImg = null;
                                try { simImg = rsSim.getString("image_url"); } catch(Exception e){}
                                if (simImg == null || simImg.trim().isEmpty()) {
                                    try { simImg = rsSim.getString("image"); } catch(Exception e){}
                                }
                                if (simImg != null && !simImg.trim().isEmpty() && !simImg.contains("/")) {
                                    simImg = "img/" + simImg;
                                }

                                similarList.add(new DetailedProduct(simId, simName, simBrand, simPrice, simCat, simImg, ""));
                            }
                        }
                    }
                    
                    // Secure fall-back: If no similar products exist under that specific category, load other products instead of leaving it blank
                    if (similarList.isEmpty()) {
                        String randomSql = "SELECT * FROM products WHERE product_id != ? LIMIT 4";
                        try (PreparedStatement psRand = conn.prepareStatement(randomSql)) {
                            psRand.setInt(1, product.getId());
                            try (ResultSet rsRand = psRand.executeQuery()) {
                                while (rsRand.next()) {
                                    int simId = rsRand.getInt("product_id");
                                    String simName = rsRand.getString("name");
                                    String simBrand = rsRand.getString("brand");
                                    double simPrice = rsRand.getDouble("price");
                                    
                                    String simImg = rsRand.getString("image_url");
                                    if (simImg == null || simImg.trim().isEmpty()) {
                                        try { simImg = rsRand.getString("image"); } catch(Exception e){}
                                    }
                                    if (simImg != null && !simImg.trim().isEmpty() && !simImg.contains("/")) {
                                        simImg = "img/" + simImg;
                                    }
                                    
                                    similarList.add(new DetailedProduct(simId, simName, simBrand, simPrice, product.getCategory(), simImg, ""));
                                }
                            }
                        }
                    }
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If the database returns no row for that ID, redirect back to catalog safety boundary
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/product");
            return;
        }

        // Send parameters straight to the existing JSP view wrapper
        request.setAttribute("selectedProduct", product);
        request.setAttribute("similarProductsList", similarList);
        request.getRequestDispatcher("/WEB-INF/views/productDetails.jsp").forward(request, response);
    }

    public static class DetailedProduct {
        private final int id;
        private final String name;
        private final String brand;
        private final double price;
        private final String category;
        private final String imageUrl;
        private final String description;

        public DetailedProduct(int id, String name, String brand, double price, String category, String imageUrl, String description) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.category = category;
            this.imageUrl = imageUrl;
            this.description = description;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public String getImageUrl() { return imageUrl; }
        public String getDescription() { return description; }
    }
}