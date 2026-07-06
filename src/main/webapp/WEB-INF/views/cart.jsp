<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.controller.CartServlet.CartItem" %>
<link rel="icon" type="image/jpeg" href="${pageContext.request.contextPath}/assets/images/img/favicon.jpg">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/cart.css">
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

    <main class="cart-workspace-container">
        <h1>Your Shopping Cart</h1>

        <div class="cart-split-layout">
            <div class="cart-items-panel">
                <%
                    List<CartItem> cart = (List<CartItem>) session.getAttribute("cartList");
                    if (cart != null && !cart.isEmpty()) {
                %>
                    <table class="cart-table-element">
                        <thead>
                            <tr>
                                <th>Product Details</th>
                                <th>Size</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (CartItem item : cart) { %>
                                <tr>
                                    <td>
                                        <div class="table-product-cell">
                                            <img src="${pageContext.request.contextPath}/assets/images/<%= item.getImageUrl() %>" 
                                                 alt="<%= item.getName() %>" 
                                                 onerror="this.onerror=null; this.src='https://placehold.co/100x100/2d2d2d/10b981?text=Item';" />
                                            <div>
                                                <h4><%= item.getName() %></h4>
                                                <p><%= item.getBrand() %></p>
                                            </div>
                                        </div>
                                    </td>
                                    <td><span class="badge-size-indicator"><%= item.getSize() %></span></td>
                                    <td>
                                        <div class="quantity-stepper-control">
                                            <button type="button" onclick="changeQuantity(<%= item.getId() %>, '<%= item.getSize() %>', <%= item.getQuantity() - 1 %>)">-</button>
                                            <input type="text" value="<%= item.getQuantity() %>" readonly />
                                            <button type="button" onclick="changeQuantity(<%= item.getId() %>, '<%= item.getSize() %>', <%= item.getQuantity() + 1 %>)">+</button>
                                        </div>
                                    </td>
                                    <td class="calculated-price-cell">₹ <%= String.format("%.2f", item.getPrice() * item.getQuantity()) %></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/cart/remove" method="POST" style="margin:0;">
                                            <input type="hidden" name="productId" value="<%= item.getId() %>">
                                            <input type="hidden" name="size" value="<%= item.getSize() %>">
                                            <button type="submit" class="btn-remove-item">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="empty-cart-state">
                        <p>Your shopping cart is currently empty. Explore items to get started!</p>
                        <a href="${pageContext.request.contextPath}/product" class="btn-shop-now">Shop Collection Now</a>
                    </div>
                <% } %>
            </div>

            <aside class="cart-summary-panel">
                <h3>Order Summary</h3>
                <div class="summary-row-line">
                    <span>Items Subtotal</span>
                    <span>₹ <%= String.format("%.2f", request.getAttribute("subtotal") != null ? (Double)request.getAttribute("subtotal") : 0.0) %></span>
                </div>
                <div class="summary-row-line">
                    <span>Standard Delivery</span>
                    <span>₹ <%= String.format("%.2f", request.getAttribute("deliveryCharges") != null ? (Double)request.getAttribute("deliveryCharges") : 0.0) %></span>
                </div>
                <hr class="summary-divider" />
                <div class="summary-row-line total-highlight">
                    <span>Total Estimated Charges</span>
                    <span>₹ <%= String.format("%.2f", request.getAttribute("totalCharges") != null ? (Double)request.getAttribute("totalCharges") : 0.0) %></span>
                </div>
                
                <%-- WIRED UP LINK LINK TO SEND THE USER TO THE CHECKOUT FLOW --%>
                <% if (cart != null && !cart.isEmpty()) { %>
                    <a href="${pageContext.request.contextPath}/checkout" style="text-decoration: none; display: block; width: 100%;">
                        <button type="button" class="btn-proceed-checkout" style="width: 100%; cursor: pointer;">Proceed to Secure Checkout</button>
                    </a>
                <% } else { %>
                    <button type="button" class="btn-proceed-checkout" disabled style="width: 100%;">Proceed to Secure Checkout</button>
                <% } %>
            </aside>
        </div>
    </main>

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

    <form id="globalUpdateForm" action="${pageContext.request.contextPath}/cart/update" method="POST" style="display:none;">
        <input type="hidden" id="updateId" name="productId" />
        <input type="hidden" id="updateSize" name="size" />
        <input type="hidden" id="updateQty" name="quantity" />
    </form>

    <script>
        function changeQuantity(id, size, newQty) {
            if (newQty < 1) return;
            document.getElementById('updateId').value = id;
            document.getElementById('updateSize').value = size;
            document.getElementById('updateQty').value = newQty;
            document.getElementById('globalUpdateForm').submit();
        }
    </script>

</body>
</html>