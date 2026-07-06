<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.controller.ProductServlet.ProductItem" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Products Catalog</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/product.css">
    <link rel="icon" type="image/jpeg" href="${pageContext.request.contextPath}/assets/images/img/favicon.jpg">
    <style>
        .nav-links a {
            position: relative;
            padding-bottom: 4px;
        }
        .nav-links a.active-link::after {
            content: '';
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            height: 2px;
            background-color: #10b981;
            border-radius: 2px;
        }
        .img-preview-box img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }
    </style>
</head>
<body>

<header class="global-header">
    <a href="${pageContext.request.contextPath}/home" class="logo">Fashion Store</a>
    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/home" 
           style="<%= request.getRequestURI().contains("/home") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Home</a>
        
        <a href="${pageContext.request.contextPath}/product" 
           style="<%= request.getRequestURI().contains("/product") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Products</a>
        
        <a href="${pageContext.request.contextPath}/cart" 
           style="<%= request.getRequestURI().contains("/cart") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Cart</a>
        
        <% if (session.getAttribute("currentUser") != null) { %>
            <span style="font-weight: bold; color: #10b981; margin-left: 24px;">
                Hi, <%= session.getAttribute("currentUser") %>
            </span>
            <a href="${pageContext.request.contextPath}/logout" style="color: #ef4444; margin-left: 15px; font-weight: bold;">Logout</a>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/login" 
               style="<%= request.getRequestURI().contains("/login") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Login</a>
        <% } %>
    </nav>
</header>

    <% if (session.getAttribute("loginSuccessMessage") != null) { %>
        <div id="loginToast" style="
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #10b981;
            color: #1a1919;
            padding: 16px 24px;
            border-radius: 8px;
            font-family: sans-serif;
            font-weight: bold;
            font-size: 14px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            z-index: 10000;
            opacity: 0;
            transform: translateY(-20px);
            transition: opacity 0.3s ease, transform 0.3s ease;
        ">
            <%= session.getAttribute("loginSuccessMessage") %>
        </div>

        <script>
            window.addEventListener('DOMContentLoaded', function() {
                var toast = document.getElementById('loginToast');
                setTimeout(function() {
                    toast.style.opacity = '1';
                    toast.style.transform = 'translateY(0)';
                }, 100);
                setTimeout(function() {
                    toast.style.opacity = '0';
                    toast.style.transform = 'translateY(-20px)';
                }, 3000);
            });
        </script>
        <% session.removeAttribute("loginSuccessMessage"); %>
    <% } %>

    <div class="explore-banner">
        <h1>Explore Our Fashion Collection</h1>
        <p>Browse products dynamically, filter by category, or sort values instantly.</p>
    </div>

    <div class="catalog-container">
        
        <aside class="sidebar-filter-panel">
            <h3>Filters</h3>
            <form action="${pageContext.request.contextPath}/product" method="GET">
                
                <div class="filter-group">
                    <label>Search</label>
                    <input type="text" name="search" placeholder="Search product or brand..." value="${param.search}">
                </div>

                <div class="filter-group">
                    <label>Category Selection</label>
                    <select name="category">
                        <option value="All Categories" <%= request.getParameter("category") == null || "All Categories".equalsIgnoreCase(request.getParameter("category")) ? "selected" : "" %>>All Categories</option>
                        <option value="Men" <%= "Men".equalsIgnoreCase(request.getParameter("category")) ? "selected" : "" %>>Men</option>
                        <option value="Women" <%= "Women".equalsIgnoreCase(request.getParameter("category")) ? "selected" : "" %>>Women</option>
                        <option value="Kids" <%= "Kids".equalsIgnoreCase(request.getParameter("category")) ? "selected" : "" %>>Kids</option>
                        <option value="Accessories" <%= "Accessories".equalsIgnoreCase(request.getParameter("category")) ? "selected" : "" %>>Accessories</option>
                    </select>
                </div>

                <div class="filter-group">
                    <label>Sort By</label>
                    <select name="sort">
                        <%-- 🌟 REPLACED: Changed "Default Order" text directly to "Default" as requested --%>
                        <option value="default" <%= "default".equals(request.getParameter("sort")) || request.getParameter("sort") == null ? "selected" : "" %>>Default</option>
                        <option value="low_to_high" <%= "low_to_high".equals(request.getParameter("sort")) ? "selected" : "" %>>Price: Low to High</option>
                        <option value="high_to_low" <%= "high_to_low".equals(request.getParameter("sort")) ? "selected" : "" %>>Price: High to Low</option>
                    </select>
                </div>

                <%-- 🌟 ADDED REQUIREMENT: Min Price and Max Price inputs explicitly placed right below Sort By segment --%>
                <div class="filter-group">
                    <label>Min Price (₹)</label>
                    <input type="number" name="minPrice" placeholder="0" value="${param.minPrice}" min="0">
                </div>

                <div class="filter-group">
                    <label>Max Price (₹)</label>
                    <input type="number" name="maxPrice" placeholder="99999" value="${param.maxPrice}" min="0">
                </div>

                <div class="action-btn-cluster">
                    <button type="submit" class="btn-submit-filter">Apply Filters</button>
                    <a href="${pageContext.request.contextPath}/product" class="btn-reset-filter">Reset Filters</a>
                </div>
            </form>
        </aside>

        <main class="showcase-content-area">
            <div class="showcase-meta-header">
                <h2>Products Marketplace</h2>
                <span class="found-counter">
                    <% 
                        List<ProductItem> list = (List<ProductItem>) request.getAttribute("productsList");
                        out.print(list != null ? list.size() : 0);
                    %> items cataloged
                </span>
            </div>

            <div class="catalog-display-grid">
                <% 
                    if (list != null && !list.isEmpty()) {
                        for (ProductItem item : list) {
                %>
                    <div class="catalog-product-card">
                        <div class="img-preview-box">
                            <%-- 🌟 FIXED PATH: Assumes item.getImageUrl() evaluates cleanly to strings like 'img/shirt.jpg' --%>
                            <img src="${pageContext.request.contextPath}/assets/images/<%= item.getImageUrl() %>" 
                                 alt="<%= item.getName() %>" 
                                 onerror="this.onerror=null; this.src='https://placehold.co/400x400/e8f2ee/0b3c2c?text=<%= item.getName() %>';" />
                        </div>
                        <div class="card-text-body">
                            <h4><%= item.getName() %></h4>
                            <p class="brand-sub-heading"><%= item.getBrand() %></p>
                            <div class="price-amount-lbl">₹ <%= String.format("%.2f", item.getPrice()) %></div>
                            
                            <a href="${pageContext.request.contextPath}/product/details?productId=<%= item.getId() %>" class="btn-go-details">View Details</a>
                        </div>
                    </div>
                <% 
                        }
                    } else {
                %>
                    <div class="empty-state-notice">
                        <p>No products match your custom search criteria. Try a different query term!</p>
                    </div>
                <% 
                    } 
                %>
            </div>
        </main>
    </div>

    <footer class="global-footer">
        <div>
            <strong>Fashion Store</strong><br>
            <span style="font-size:12px; opacity:0.7;">Your premium design platform layout.</span>
        </div>
        <div>
            <a href="#">Privacy Policy</a>
            <a href="#">Terms & Conditions</a>
        </div>
    </footer>

</body>
</html>