
package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cartList");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cartList", cart);
        }

        // Calculate checkout metrics automatically whenever the cart is accessed
        double subtotal = 0.0;
        for (CartItem item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        
        // 🌟 UPDATED: Match explicit delivery & handling fee (₹200.00)
        double deliveryCharges = subtotal > 0 ? 200.0 : 0.0; 
        double totalCharges = subtotal + deliveryCharges;

        request.setAttribute("subtotal", subtotal);
        request.setAttribute("deliveryCharges", deliveryCharges);
        request.setAttribute("totalCharges", totalCharges);

        // Safely forward down to the UI layout view
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cartList");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cartList", cart);
        }

        String pathInfo = request.getPathInfo();

        if ("/add".equals(pathInfo)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String size = request.getParameter("selectedSize");
            if (size == null || size.trim().isEmpty()) size = "M";

            // Read dynamic parameters passed from the product details form
            String pName = request.getParameter("productName");
            String pBrand = request.getParameter("productBrand");
            String pPriceStr = request.getParameter("productPrice");
            String pImage = request.getParameter("productImage");

            // 🌟 FIXED: Dynamic fallbacks ensuring each product ID is treated uniquely
            if (pName == null || pName.trim().isEmpty()) {
                pName = (productId == 2) ? "Blue Slim Fit Jeans" : (productId == 1 ? "Black Casual Shirt" : "Product #" + productId);
            }
            if (pBrand == null || pBrand.trim().isEmpty()) {
                pBrand = (productId == 2) ? "Levis" : (productId == 1 ? "Puma" : "Fashion Store");
            }
            
            double price = 0.0;
            if (pPriceStr != null && !pPriceStr.trim().isEmpty()) {
                try {
                    price = Double.parseDouble(pPriceStr.trim());
                } catch (NumberFormatException e) {
                    price = (productId == 2) ? 960.00 : 450.00;
                }
            } else {
                price = (productId == 2) ? 960.00 : 450.00;
            }
            
            if (pImage == null || pImage.trim().isEmpty()) {
                pImage = (productId == 2) ? "jeans.jpg" : "shirt.jpg";
            }

            CartItem newItem = new CartItem(productId, pName, pBrand, price, size, 1, pImage);

            // Check if item with same ID and size exists to merge quantity
            boolean exists = false;
            for (CartItem item : cart) {
                if (item.getId() == newItem.getId() && item.getSize().equalsIgnoreCase(newItem.getSize())) {
                    item.setQuantity(item.getQuantity() + 1);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                cart.add(newItem); // Adds unique selected product as a separate line item
            }

        } else if ("/update".equals(pathInfo)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String size = request.getParameter("size");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            for (CartItem item : cart) {
                if (item.getId() == productId && item.getSize().equalsIgnoreCase(size)) {
                    if (quantity > 0) {
                        item.setQuantity(quantity);
                    }
                    break;
                }
            }

        } else if ("/remove".equals(pathInfo)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String size = request.getParameter("size");

            cart.removeIf(item -> item.getId() == productId && item.getSize().equalsIgnoreCase(size));
        }

        // Force browser context back to Servlet doGet execution cycle method 
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    public static class CartItem {
        private final int id;
        private final String name;
        private final String brand;
        private final double price;
        private final String size;
        private int quantity;
        private final String imageUrl;

        public CartItem(int id, String name, String brand, double price, String size, int quantity, String imageUrl) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.size = size;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public double getPrice() { return price; }
        public String getSize() { return size; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int q) { this.quantity = q; }
        public String getImageUrl() { return imageUrl; }
    }
}
