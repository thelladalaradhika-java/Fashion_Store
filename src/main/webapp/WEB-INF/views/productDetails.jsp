<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.controller.ProductDetailsServlet.DetailedProduct" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Product Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/details.css">
    <link rel="icon" type="image/jpeg" href="${pageContext.request.contextPath}/assets/images/img/favicon.jpg">
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

    <div class="master-details-container">
        <%
            DetailedProduct item = (DetailedProduct) request.getAttribute("selectedProduct");
            if (item != null) {
                // 🌟 FIXED: Pull directly from database path with zero override rules
                String mainImg = item.getImageUrl();
        %>
            <div class="product-showcase-panel">
                
                <div class="details-image-panel">
                    <img src="${pageContext.request.contextPath}/assets/images/<%= mainImg %>" 
                         alt="<%= item.getName() %>"
                         onerror="this.onerror=null; this.src='https://placehold.co/600x600/e8f2ee/0b3c2c?text=<%= item.getName() %>';" />
                </div>

                <div class="details-info-panel">
                    <span class="brand-sub-header"><%= item.getBrand() %></span>
                    <h1><%= item.getName() %></h1>
                    
                    <div class="price-amount-lbl">
                        ₹ <%= String.format("%.2f", item.getPrice()) %>
                    </div>

                    <div class="product-description-container">
                        <h3>Description</h3>
                        <p><%= item.getDescription() %></p>
                    </div>

                    <div class="sizes-specification-group">
                        <h3>Available Sizes</h3>
                        <div class="size-chips-flexrow">
                            <span class="size-chip-box" data-size="S">S</span>
                            <span class="size-chip-box active" data-size="M">M</span>
                            <span class="size-chip-box" data-size="L">L</span>
                            <span class="size-chip-box" data-size="XL">XL</span>
                        </div>
                    </div>

                    <form action="${pageContext.request.contextPath}/cart/add" method="POST" style="margin-top: 30px;">
                        <input type="hidden" name="productId" value="<%= item.getId() %>">
                        
                        <input type="hidden" id="selectedSizeInput" name="selectedSize" value="M">
                        
                        <div class="cta-button-deck">
                            <button type="submit" class="btn-add-to-cart">Add to Cart</button>
                            <a href="${pageContext.request.contextPath}/product" class="btn-return-catalog">Back to Products</a>
                        </div>
                    </form>
                </div>
            </div>

            <section class="similar-products-section">
                <div class="section-divider-title">
                    <%-- 🌟 ADDED: Fire icon included directly on header title --%>
                    <h2>🔥 Similar Products</h2>
                    <span class="accent-underline-bar"></span>
                </div>
                
                <div class="similar-grid-display">
                    <% 
                        List<DetailedProduct> similarItems = (List<DetailedProduct>) request.getAttribute("similarProductsList");
                        if (similarItems != null && !similarItems.isEmpty()) {
                            for (DetailedProduct simItem : similarItems) {
                                // 🌟 FIXED: Pure database tracking mapping loop for image assets
                                String simImg = simItem.getImageUrl();
                    %>
                        <div class="similar-product-card">
                            <div class="similar-img-box">
                                <img src="${pageContext.request.contextPath}/assets/images/<%= simImg %>" 
                                     alt="<%= simItem.getName() %>" 
                                     onerror="this.onerror=null; this.src='https://placehold.co/400x400/e8f2ee/0b3c2c?text=<%= simItem.getName() %>';" />
                            </div>
                            <div class="similar-card-body">
                                <h4><%= simItem.getName() %></h4>
                                <div class="similar-price-lbl">₹ <%= String.format("%.2f", simItem.getPrice()) %></div>
                                <a href="${pageContext.request.contextPath}/product/details?productId=<%= simItem.getId() %>" class="btn-view-similar">View Product</a>
                            </div>
                        </div>
                    <% 
                            }
                        } else {
                    %>
                        <p style="grid-column: span 3; text-align: center; color: #94a3b8; padding: 30px 0; font-weight: 500;">No similar products available in this category yet.</p>
                    <% 
                        } 
                    %>
                </div>
            </section>

        <% } else { %>
            <div class="error-details-fallback">
                <p>No specifications details could be retrieved from active catalog data rows.</p>
                <a href="${pageContext.request.contextPath}/product" class="btn-return-catalog">Back to Products</a>
            </div>
        <% } %>
    </div>

    <footer class="global-footer">
        <div>
            <strong>Fashion Store</strong><br>
            <span style="font-size:12px; opacity:0.7;">Your one-stop destination for stylish fashion.</span>
        </div>
        <div>
            <a href="#">Privacy Policy</a>
            <a href="#">Terms & Conditions</a>
        </div>
    </footer>

    <script>
        document.querySelectorAll('.size-chip-box').forEach(chip => {
            chip.addEventListener('click', function() {
                document.querySelectorAll('.size-chip-box').forEach(c => c.classList.remove('active'));
                this.classList.add('active');
                const pickedSize = this.getAttribute('data-size');
                document.getElementById('selectedSizeInput').value = pickedSize;
            });
        });
    </script>

</body>
</html>