<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.controller.HomeServlet.HomeProduct" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Home</title>
    <link class="favicon" rel="icon" type="image/png" href="https://cdn-icons-png.flaticon.com/512/3081/3081840.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="icon" type="image/jpeg" href="${pageContext.request.contextPath}/assets/images/img/favicon.jpg">
    <style>
        .category-grid {
            display: flex !important;
            justify-content: space-between !important; 
            align-items: flex-start !important;
            gap: 24px !important;
            padding: 25px 0px 35px 0px !important;
            margin-bottom: 40px !important;
            width: 100% !important;
            box-sizing: border-box !important;
        }
        .category-card {
            flex: 1 !important; 
            max-width: 24% !important;
            display: flex !important;
            flex-direction: column !important;
            align-items: center !important;
            text-align: center !important;
            cursor: pointer !important;
            transition: transform 0.3s cubic-bezier(0.25, 1, 0.5, 1) !important;
        }
        .category-img-box {
            width: 100% !important;
            height: 290px !important; 
            overflow: hidden !important;
            background-color: #f6eee8 !important; 
            position: relative !important;
            border-top-left-radius: 140px !important;
            border-top-right-radius: 140px !important;
            border-bottom-left-radius: 20px !important;
            border-bottom-right-radius: 20px !important;
            border: 2px solid #e1d6ce !important; 
            box-shadow: 0 12px 24px -4px rgba(165, 140, 125, 0.2), 0 4px 12px -2px rgba(0, 0, 0, 0.05) !important;
        }
        .category-img-box img {
            width: 100% !important;
            height: 100% !important;
            object-fit: cover !important;
        }
        .category-card:hover { transform: translateY(-6px) !important; }
        .category-info { padding: 15px 4px 0 4px !important; width: 100% !important; }
        .category-info h4 { margin: 0 0 4px 0 !important; font-size: 17px !important; color: #1e293b !important; }
        .category-info p { margin: 0 0 8px 0 !important; font-size: 13px !important; color: #64748b !important; }
        .img-box { height: 280px; width: 100%; background-color: #f8fafc; overflow: hidden; display: flex; align-items: center; justify-content: center; }
        .img-box img { width: 100%; height: 100%; object-fit: cover; }

        /* 🌟 REQUIRED FIX: Forces banner to occupy 100% width of the entire row with zero blank gaps */
        .hero-banner {
            width: 100% !important;
            height: auto !important;
            max-height: 480px !important;
            overflow: hidden !important;
            box-sizing: border-box !important;
            margin: 20px 0 40px 0 !important;
            border-radius: 14px !important;
            box-shadow: 0 10px 25px rgba(0,0,0,0.12) !important;
            display: block !important;
            padding: 0 !important;
            background: none !important;
            border: none !important;
        }
        .hero-banner img {
            width: 100% !important;
            height: 100% !important;
            display: block !important;
            object-fit: cover !important;
            /* Handles smooth interactive premium scaling */
            transition: transform 0.5s cubic-bezier(0.25, 1, 0.5, 1) !important;
        }
        /* Micro-interaction animation effect on user hover */
        .hero-banner:hover img {
            transform: scale(1.02) !important;
        }
        
        .explore-link {
            font-size: 14px !important;
            color: #4f46e5 !important;
            text-decoration: none !important;
            font-weight: 600 !important;
            display: inline-block !important;
            margin-top: 6px !important;
        }
        .explore-link:hover { text-decoration: underline !important; }
    </style>
</head>
<body>

<header class="global-header">
    <a href="${pageContext.request.contextPath}/home" class="logo">Fashion Store</a>
    <nav class="nav-links">
        <% 
            String uri = request.getRequestURI();
            boolean isHome = uri.endsWith("/home") || uri.endsWith("/") || uri.equals(request.getContextPath() + "/");
        %>
        <a href="${pageContext.request.contextPath}/home" style="<%= isHome ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Home</a>
        <a href="${pageContext.request.contextPath}/product" style="<%= uri.contains("/product") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Products</a>
        <a href="${pageContext.request.contextPath}/cart" style="<%= uri.contains("/cart") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Cart</a>
        
        <% if (session.getAttribute("currentUser") != null) { %>
            <span style="font-weight: bold; color: #10b981; margin-left: 24px;">Hi, <%= session.getAttribute("currentUser") %></span>
            <a href="${pageContext.request.contextPath}/logout" style="color: #ef4444; margin-left: 15px; font-weight: bold;">Logout</a>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/login" style="<%= uri.contains("/login") ? "border-bottom: 2px solid #ffffff; padding-bottom: 4px;" : "" %>">Login</a>
        <% } %>
    </nav>
</header>

    <main class="section-wrap">
        
        <%-- 🛠️ FIXED: Sets the row element to target image_b423fe.jpg exclusively --%>
        <div class="hero-banner">
            <img src="${pageContext.request.contextPath}/assets/images/img/image_b423fe.jpg" 
                 alt="Fashion Banner" 
                 onerror="this.onerror=null; this.src='assets/images/img/image_b423fe.jpg';">
        </div>

        <h3>Shop by Category</h3>
        <div class="category-grid">
            
            <%-- ACCESSORIES --%>
            <div class="category-card" onclick="window.location.href='${pageContext.request.contextPath}/product?category=Accessories'">
                <div class="category-img-box"><img src="${pageContext.request.contextPath}/assets/images/img/image_5212ac.jpg" alt="Accessories"></div>
                <div class="category-info">
                    <h4>Accessories</h4>
                    <p>Premium Watches & Bags</p>
                    <a class="explore-link" href="${pageContext.request.contextPath}/product?category=Accessories">Explore &rarr;</a>
                </div>
            </div>
            
            <%-- KIDS --%>
            <div class="category-card" onclick="window.location.href='${pageContext.request.contextPath}/product?category=Kids'">
                <div class="category-img-box"><img src="${pageContext.request.contextPath}/assets/images/img/image_52128d.jpg" alt="Kids"></div>
                <div class="category-info">
                    <h4>Kids</h4>
                    <p>Colorful & Playful Apparels</p>
                    <a class="explore-link" href="${pageContext.request.contextPath}/product?category=Kids">Explore &rarr;</a>
                </div>
            </div>
            
            <%-- MEN --%>
            <div class="category-card" onclick="window.location.href='${pageContext.request.contextPath}/product?category=Men'">
                <div class="category-img-box"><img src="${pageContext.request.contextPath}/assets/images/img/image_52124f.jpg" alt="Men"></div>
                <div class="category-info">
                    <h4>Men</h4>
                    <p>Smart Casuals & Formals</p>
                    <a class="explore-link" href="${pageContext.request.contextPath}/product?category=Men">Explore &rarr;</a>
                </div>
            </div>
            
            <%-- WOMEN --%>
            <div class="category-card" onclick="window.location.href='${pageContext.request.contextPath}/product?category=Women'">
                <div class="category-img-box"><img src="${pageContext.request.contextPath}/assets/images/img/image_52126b.png" alt="Women" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/assets/images/img/image_52126b.jpg';"></div>
                <div class="category-info">
                    <h4>Women</h4>
                    <p>Elegant Dresses & Trends</p>
                    <a class="explore-link" href="${pageContext.request.contextPath}/product?category=Women">Explore &rarr;</a>
                </div>
            </div>
        </div>

        <h3>Our Collections</h3>
        <div class="product-grid">
            <% 
                List<HomeProduct> featured = (List<HomeProduct>) request.getAttribute("featuredItems");
                if (featured != null && !featured.isEmpty()) {
                    for (HomeProduct item : featured) {
            %>
                <div class="product-card">
                    <div class="img-box">
                        <img src="${pageContext.request.contextPath}/assets/images/<%= item.getImageUrl() %>" 
                             alt="<%= item.getTitle() %>"
                             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/assets/images/img/shirt.jpg';" />
                    </div>
                    <div class="prod-details">
                        <h4><%= item.getTitle() %></h4>
                        <p class="brand-text"><%= item.getBrand() %></p>
                        <div class="price-text">₹ <%= String.format("%.2f", item.getPrice()) %></div>
                        <a href="${pageContext.request.contextPath}/product/details?productId=<%= item.getId() %>" class="btn-view">View Details</a>
                    </div>
                </div>
            <% 
                    }
                } else {
            %>
                <p style="grid-column: span 4; text-align: center; color: #94a3b8; padding: 40px; font-weight: 500;">No featured products found.</p>
            <% } %>
        </div>
    </main>

    <footer class="global-footer">
        <div><strong>Fashion Store</strong><br><span style="font-size:12px; color:#a7f3d0; opacity:0.8;">Your one-stop destination for stylish fashion.</span></div>
    </footer>
</body>
</html>