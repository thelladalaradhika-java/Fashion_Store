package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Radhikamohan@02";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String sortBy = request.getParameter("sort");
        
        // 🌟 REQUIREMENT: Capture input minPrice and maxPrice filters from UI
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");

        List<ProductItem> productList = new ArrayList<>();
        
        // 🌟 SYNCHRONIZED PATHS: Prefixed with 'img/' to match your workspace folder hierarchy exactly
        List<ProductItem> masterDataset = new ArrayList<>();
        masterDataset.add(new ProductItem(1, "Black Casual Shirt", "Puma", 450.00, "Men", "img/shirt.jpg"));
        masterDataset.add(new ProductItem(2, "Blue Slim Fit Jeans", "Levis", 960.00, "Men", "img/jeans.jpg"));
        masterDataset.add(new ProductItem(3, "White Polo T-Shirt", "Zara", 1275.00, "Men", "img/polo.jpg"));
        masterDataset.add(new ProductItem(4, "Floral Summer Dress", "Zara", 1500.00, "Women", "img/dress.jpg"));
        masterDataset.add(new ProductItem(5, "Kids Denim Jacket", "Adidas", 890.00, "Kids", "img/jacket.jpg"));
        masterDataset.add(new ProductItem(6, "Leather Handbag", "Gucci", 4500.00, "Accessories", "img/bag.jpg"));

        // Step A: Attempt Live Database Table Extraction
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM products");
                 ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    // 🌟 FIXED: Reading columns by explicit String Names to prevent mismatched data types or index shifts
                    int id = rs.getInt("product_id");
                    String name = rs.getString("name");
                    String brand = rs.getString("brand");
                    double price = rs.getDouble("price");
                    
                    // Fallback reader to safely translate column variants (text name vs ID integers) cleanly
                    String cat = "General";
                    try {
                        cat = rs.getString("category");
                        if (cat == null || cat.trim().isEmpty() || "1".equals(cat) || "2".equals(cat) || "3".equals(cat) || "4".equals(cat)) {
                            int catId = rs.getInt("category_id");
                            if (catId == 1) cat = "Men";
                            else if (catId == 2) cat = "Women";
                            else if (catId == 3) cat = "Kids";
                            else if (catId == 4) cat = "Accessories";
                        }
                    } catch(Exception e) {
                        try { cat = rs.getString("category_name"); } catch(Exception ex){}
                    }
                    
                    String img = "img/shirt.jpg"; 
                    try { 
                        img = rs.getString("image_url"); 
                    } catch(Exception e){}
                    
                    // Safe-guard to fix un-prefixed database values on the fly
                    if (img != null && !img.contains("/")) {
                        img = "img/" + img;
                    }
                    
                    productList.add(new ProductItem(id, name, brand, price, cat, img));
                }
            }
        } catch (Exception e) {
            System.out.println("SQL connection offline or unpopulated. Proceeding to fallback core arrays.");
        }

        // Step B: Use backup matrix if database yields nothing
        if (productList.isEmpty()) {
            productList.addAll(masterDataset);
        }

        // Step C: Execute Active Searching, Category Filter, and Price Bisection checks
        List<ProductItem> filteredList = new ArrayList<>();
        
        // Parse min/max values safely
        double minPrice = 0.0;
        double maxPrice = Double.MAX_VALUE;
        if (minPriceParam != null && !minPriceParam.trim().isEmpty()) {
            try { minPrice = Double.parseDouble(minPriceParam.trim()); } catch(NumberFormatException e){}
        }
        if (maxPriceParam != null && !maxPriceParam.trim().isEmpty()) {
            try { maxPrice = Double.parseDouble(maxPriceParam.trim()); } catch(NumberFormatException e){}
        }

        for (ProductItem item : productList) {
            boolean matchesCategory = true;
            boolean matchesSearch = true;
            boolean matchesPrice = true; // 🌟 REQUIREMENT: Check against price range bounds

            if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All Categories")) {
                if (!item.getCategory().equalsIgnoreCase(category.trim())) {
                    matchesCategory = false;
                }
            }

            if (search != null && !search.trim().isEmpty()) {
                String searchLower = search.toLowerCase().trim();
                if (!item.getName().toLowerCase().contains(searchLower) && 
                    !item.getBrand().toLowerCase().contains(searchLower)) {
                    matchesSearch = false;
                }
            }
            
            // 🌟 REQUIREMENT: Filter item evaluation based on price boundaries
            if (item.getPrice() < minPrice || item.getPrice() > maxPrice) {
                matchesPrice = false;
            }

            if (matchesCategory && matchesSearch && matchesPrice) {
                filteredList.add(item);
            }
        }

        // Step D: Apply Sorting rules
        if ("low_to_high".equals(sortBy) || "lowToHigh".equals(sortBy)) {
            filteredList.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        } else if ("high_to_low".equals(sortBy) || "highToLow".equals(sortBy)) {
            filteredList.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        } else {
            // 🌟 RESTORED: Default rule sorting strictly by sequential Product IDs when sort value is "default" or empty
            filteredList.sort((a, b) -> Integer.compare(a.getId(), b.getId()));
        }

        request.setAttribute("productsList", filteredList);
        request.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(request, response);
    }

    public static class ProductItem {
        private final int id;
        private final String name;
        private final String brand;
        private final double price;
        private final String category;
        private final String imageUrl;

        public ProductItem(int id, String name, String brand, double price, String category, String imageUrl) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.category = category;
            this.imageUrl = imageUrl;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public String getImageUrl() { return imageUrl; }
    }
}